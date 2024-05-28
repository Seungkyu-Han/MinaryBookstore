package np.minarybook.application.impl

import np.minarybook.application.BookService
import np.minarybook.model.dto.book.req.BookPostReq
import np.minarybook.model.dto.book.res.*
import np.minarybook.model.dto.bookForRent.res.BookForRentGetRes
import np.minarybook.model.dto.bookForSale.res.BookForSaleGetRes
import np.minarybook.model.dto.image.res.ImageGetElementRes
import np.minarybook.model.entity.Book
import np.minarybook.model.entity.User
import np.minarybook.model.enum.State
import np.minarybook.repository.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.http.*
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.lang.NullPointerException
import java.util.concurrent.CompletableFuture

@Service
class BookServiceImpl(
    @Value("\${kakao.auth.client_id}")
    private val key: String,
    private val bookRepository: BookRepository,
    private val bookForSaleRepository: BookForSaleRepository,
    private val bookForRentRepository: BookForRentRepository,
    private val bookForSaleSaveRepository: BookForSaleSaveRepository,
    private val bookForRentSaveRepository: BookForRentSaveRepository,
    private val imageRepository: ImageRepository
): BookService {

    private val requestUrl: String = "https://dapi.kakao.com/v3/search/book"
    private val bestSellerElementCount = 5
    override fun getUpload(authentication: Authentication): ResponseEntity<BookGetUploadRes> {
        val user = User(authentication.name.toLong())
        val bookForSaleList = bookForSaleRepository.findByUser(user).map {
                bookForSale -> BookForSaleGetRes(bookForSale,
            imageRepository.findByBookForSale(bookForSale).map{
                    image -> ImageGetElementRes(image)
            }, editable = true, isSave = true)
        }
        val bookForRentList = bookForRentRepository.findByUser(user).map{
                bookForRent -> BookForRentGetRes(bookForRent,
            imageRepository.findByBookForRent(bookForRent).map{
                    image -> ImageGetElementRes(image)
            }, editable = true, isSave = true
        )}
        return ResponseEntity.ok(BookGetUploadRes(bookForSaleList, bookForRentList))
    }

    override fun getBest(): ResponseEntity<List<BookGetBestRes>> {
        val bestSeller = bookRepository.findByBestSeller(State.SOLD, PageRequest.of(0, bestSellerElementCount))
        val notBestSeller = bookRepository.findByNotBestSeller(state = State.SOLD,
            pageable =  PageRequest.of(0, bestSellerElementCount - bestSeller.size),
            bestSeller.map { book -> book.id ?: 0 })
        return ResponseEntity(bestSeller.plus(notBestSeller)
            .map {
                    book ->
                BookGetBestRes(book, bookRepository.countBestSeller(book.id ?: 0, State.SOLD))
            }, HttpStatus.OK)
    }

    override fun getIsbn(isbn: String): ResponseEntity<BookGetRes> {
        val serverFuture = CompletableFuture.supplyAsync {
            getFromServer(isbn)
        }
        val dbFuture = CompletableFuture.supplyAsync {
            getFromDB(isbn)
        }

        return if(dbFuture.get() == null){
            val book = serverFuture.get()
            bookRepository.save(book)
            ResponseEntity(BookGetRes(book), HttpStatus.OK)
        } else{
            ResponseEntity(dbFuture.get(), HttpStatus.OK)
        }
    }

    override fun getTitle(title: String): ResponseEntity<BookGetRes> {
        val book = bookRepository.findTopByTitle(title)
        return if (book.isEmpty)
            ResponseEntity(HttpStatus.NOT_FOUND)
        else
            ResponseEntity(BookGetRes(book.get()), HttpStatus.OK)
    }

    override fun post(bookPostReq: BookPostReq): ResponseEntity<BookGetRes> {
        val book = Book(bookPostReq)
        bookRepository.save(book)
        return ResponseEntity(BookGetRes(book), HttpStatus.OK)
    }

    override fun getSave(authentication: Authentication): ResponseEntity<BookGetSaveRes> {
        val user = User(authentication.name.toLong())
        val bookForSaleList = bookForSaleSaveRepository.findByUser(user).map {
            bookForSaleSave -> BookForSaleGetRes(bookForSaleSave.bookForSale,
                imageRepository.findByBookForSale(bookForSaleSave.bookForSale).map{
                    image -> ImageGetElementRes(image)
                }, bookForSaleSave.bookForSale.user.id == authentication.name?.toLong(), true)
        }
        val bookForRentList = bookForRentSaveRepository.findByUser(user).map{
            bookForRentSave -> BookForRentGetRes(bookForRentSave.bookForRent,
            imageRepository.findByBookForRent(bookForRentSave.bookForRent).map{
                image -> ImageGetElementRes(image)
            }, bookForRentSave.bookForRent.user.id == authentication.name?.toLong(), true)
        }
        return ResponseEntity.ok(BookGetSaveRes(bookForSaleList, bookForRentList))
    }

    private fun getFromDB(isbn: String): BookGetRes?{
        val book = bookRepository.findByIsbnAndFind(isbn, true)
        return if(book.isEmpty)
            null
        else
            BookGetRes(book.get())

    }

    private fun getFromServer(isbn: String): Book {
        val restTemplate = RestTemplate()

        val headers = HttpHeaders()
        headers.set("Authorization", "KakaoAK $key")
        headers.contentType = MediaType.APPLICATION_JSON

        val requestUrlWithParam = UriComponentsBuilder.fromHttpUrl(requestUrl)
            .queryParam("target", "isbn")
            .queryParam("query", isbn)
            .build(true).toString()

        val requestEntity = HttpEntity<String>(headers)

        val response = restTemplate.exchange(
            requestUrlWithParam,
            HttpMethod.GET,
            requestEntity,
            ISBNSeojiRes::class.java
        )

        val isbnBookRes = response.body?.documents?.get(0)

        return Book(
            null, title = isbnBookRes?.title ?: throw NullPointerException(),
            price = isbnBookRes.price,
            author = isbnBookRes.authors.toString(),
            image = isbnBookRes.thumbnail,
            publicationDate = isbnBookRes.datetime,
            publisher = isbnBookRes.publisher,
            isbn = isbnBookRes.isbn?.split(" ")?.last(),
            find = true
        )
    }
}