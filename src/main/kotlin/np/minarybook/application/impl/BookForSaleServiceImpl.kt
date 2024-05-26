package np.minarybook.application.impl

import np.minarybook.application.BookForSaleService
import np.minarybook.model.dto.bookForSale.req.BookForSalePostReq
import np.minarybook.model.dto.bookForSale.req.BookForSalePutReq
import np.minarybook.model.dto.bookForSale.res.BookForSaleGetElementRes
import np.minarybook.model.dto.bookForSale.res.BookForSaleGetRes
import np.minarybook.model.dto.image.res.ImageGetElementRes
import np.minarybook.model.entity.Book
import np.minarybook.model.entity.BookForSale
import np.minarybook.model.entity.BookForSaleSave
import np.minarybook.model.entity.User
import np.minarybook.model.enum.Category
import np.minarybook.model.enum.State
import np.minarybook.repository.BookForSaleRepository
import np.minarybook.repository.BookForSaleSaveRepository
import np.minarybook.repository.BookRepository
import np.minarybook.repository.ImageRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class BookForSaleServiceImpl(
    private val bookForSaleRepository: BookForSaleRepository,
    private val bookRepository: BookRepository,
    private val imageRepository: ImageRepository,
    private val bookForSaleSaveRepository: BookForSaleSaveRepository,
    @Value("\${mainPageElementSize}")
    private val mainPageElementSize: Int
) : BookForSaleService{

    override fun get(id: Int, authentication: Authentication?): ResponseEntity<BookForSaleGetRes> {

        val bookForSale = bookForSaleRepository.findById(id).orElseThrow{NullPointerException()}

        val image = imageRepository.findByBookForSale(bookForSale).map { image -> ImageGetElementRes(image) }

        return ResponseEntity.ok(BookForSaleGetRes(bookForSale, image, bookForSale.user.id == authentication?.name?.toLong(), bookForSaleSaveRepository.existsByUserAndBookForSale(User(authentication?.name?.toLong() ?: -1), bookForSale)))
    }

    override fun post(
        bookForSalePostReq: BookForSalePostReq,
        authentication: Authentication
    ): ResponseEntity<HttpStatus> {

        val book = Book(bookForSalePostReq.bookId)

        val user = User(authentication.name.toLong())

        val bookForSale = BookForSale(bookForSalePostReq, book, user)

        bookForSaleRepository.save(bookForSale)

        bookForSalePostReq.imageIdList.map {
            imageId ->
            val image = imageRepository.findById(imageId).orElseThrow{NullPointerException()}
            image.bookForSale = bookForSale
            imageRepository.save(image)
        }

        return ResponseEntity(HttpStatus.OK)
    }

    override fun getList(
        category: Category?,
        authentication: Authentication?
    ): ResponseEntity<List<BookForSaleGetElementRes>> {
        val bookForSaleList: List<BookForSale> = if(category != null){
            bookForSaleRepository.findByCategoryAndSalePriceGreaterThanOrderByIdDesc(category, 0, PageRequest.of(0, mainPageElementSize))
        } else{
            bookForSaleRepository.findBySalePriceGreaterThanOrderByIdDesc(0, PageRequest.of(0, mainPageElementSize))
        }
        return ResponseEntity(
            bookForSaleList.map {bookForSale ->  BookForSaleGetElementRes(bookForSale,
            bookForSaleSaveRepository.existsByUserAndBookForSale(User(authentication?.name?.toLong() ?: -1), bookForSale)) }, HttpStatus.OK
        )
    }

    override fun delete(id: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
        bookForSaleRepository.deleteById(id)
        return ResponseEntity.ok().build()
    }

    override fun put(bookForSalePutReq: BookForSalePutReq, authentication: Authentication): ResponseEntity<HttpStatus> {
        val bookForSale = bookForSaleRepository.findById(bookForSalePutReq.id).orElseThrow { NullPointerException() }
        bookForSale.put(bookForSalePutReq)
        bookForSaleRepository.save(bookForSale)
        bookForSalePutReq.imageIdList.map {
                imageId ->
            val image = imageRepository.findById(imageId).orElseThrow { NullPointerException() }
            image.bookForSale= bookForSale
            imageRepository.save(image)
        }
        return ResponseEntity.ok().build()
    }

    override fun getShareList(
        category: Category?,
        authentication: Authentication?
    ): ResponseEntity<List<BookForSaleGetElementRes>> {
        val bookForSaleList: List<BookForSale> = if(category != null){
            bookForSaleRepository.findByCategoryAndSalePriceOrderByIdDesc(category, 0, PageRequest.of(0, mainPageElementSize))
        } else{
            bookForSaleRepository.findBySalePriceOrderByIdDesc(0, PageRequest.of(0, mainPageElementSize))
        }
        return ResponseEntity(
            bookForSaleList.map {bookForSale ->  BookForSaleGetElementRes(bookForSale,
                bookForSaleSaveRepository.existsByUserAndBookForSale(User(authentication?.name?.toLong() ?: -1), bookForSale)) }, HttpStatus.OK
        )
    }

    override fun patchSold(bookForSaleId: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
        val bookForSale:BookForSale = bookForSaleRepository.findById(bookForSaleId).orElseThrow { NullPointerException() }
        if(bookForSale.user.id != authentication.name.toLong())
            return ResponseEntity(HttpStatus.FORBIDDEN)
        bookForSale.state = State.SOLD
        bookForSaleRepository.save(bookForSale)
        return ResponseEntity(HttpStatus.OK)
    }

    override fun getSearchTitle(
        title: String,
        authentication: Authentication?
    ): ResponseEntity<List<BookForSaleGetElementRes>> {
        return ResponseEntity(bookForSaleRepository.findByBookTitle("%${title}%").map{
            bookForSale -> BookForSaleGetElementRes(bookForSale, bookForSaleSaveRepository.existsByUserAndBookForSale(User(authentication?.name?.toLong() ?: -1), bookForSale))
        }, HttpStatus.OK)
    }

    override fun getSearchIsbn(
        isbn: String,
        authentication: Authentication?
    ): ResponseEntity<List<BookForSaleGetElementRes>> {
        return ResponseEntity(bookForSaleRepository.findByBookIsbn(isbn).map{
            bookForSale -> BookForSaleGetElementRes(bookForSale, bookForSaleSaveRepository.existsByUserAndBookForSale(User(authentication?.name?.toLong() ?: -1), bookForSale))
        }, HttpStatus.OK)
    }

    override fun postSave(bookForSaleId: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
        val bookForSale = bookForSaleRepository.findById(bookForSaleId).orElseThrow { NullPointerException() }
        bookForSaleSaveRepository.save(BookForSaleSave(user = User(authentication.name.toLong()), bookForSale = bookForSale))
        return ResponseEntity(HttpStatus.OK)
    }

    override fun deleteSave(bookForSaleId: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
        val bookForSale = bookForSaleRepository.findById(bookForSaleId).orElseThrow { NullPointerException() }
        bookForSaleSaveRepository.deleteByUserAndBookForSale(User(authentication.name.toLong()), bookForSale)
        return ResponseEntity(HttpStatus.OK)
    }
}