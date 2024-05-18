package np.minarybook.application.impl

import np.minarybook.application.BookService
import np.minarybook.model.dto.book.req.BookPostReq
import np.minarybook.model.dto.book.res.BookGetRes
import np.minarybook.model.dto.book.res.ISBNSeojiRes
import np.minarybook.model.entity.Book
import np.minarybook.repository.BookRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.lang.NullPointerException
import java.util.concurrent.CompletableFuture

@Service
class BookServiceImpl(
    @Value("\${kakao.auth.client_id}")
    private val key: String,
    private val bookRepository: BookRepository
): BookService {

    private val requestUrl: String = "https://dapi.kakao.com/v3/search/book"

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