package np.minarybook.application.impl

import np.minarybook.application.BookForSaleService
import np.minarybook.model.dto.bookForSale.req.BookForSalePostReq
import np.minarybook.model.dto.bookForSale.res.BookForSaleGetElementRes
import np.minarybook.model.dto.bookForSale.res.BookForSaleGetRes
import np.minarybook.model.entity.Book
import np.minarybook.model.entity.BookForSale
import np.minarybook.repository.BookForSaleRepository
import np.minarybook.repository.BookRepository
import np.minarybook.repository.ImageRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.lang.NullPointerException

@Service
class BookForSaleServiceImpl(
    private val bookForSaleRepository: BookForSaleRepository,
    private val bookRepository: BookRepository,
    private val imageRepository: ImageRepository,
    @Value("\${mainPageElementSize}")
    private val mainPageElementSize: Int
) : BookForSaleService{

    override fun get(id: Int): ResponseEntity<BookForSaleGetRes> {

        val bookForSale = bookForSaleRepository.findById(id).orElseThrow{NullPointerException()}

        val image = imageRepository.findByBookForSale(bookForSale).map { image -> image.url }

        return ResponseEntity.ok(BookForSaleGetRes(bookForSale, image))
    }

    override fun post(
        bookForSalePostReq: BookForSalePostReq,
        authentication: Authentication
    ): ResponseEntity<HttpStatus> {

        val book = Book(bookForSalePostReq.bookId)

        val bookForSale = BookForSale(bookForSalePostReq, book)

        bookForSaleRepository.save(bookForSale)

        bookForSalePostReq.imageIdList.map {
            imageId ->
            val image = imageRepository.findById(imageId).orElseThrow{NullPointerException()}
            image.bookForSale = bookForSale
        }

        return ResponseEntity(HttpStatus.OK)
    }

    override fun getList(
        category: String?,
        authentication: Authentication
    ): ResponseEntity<List<BookForSaleGetElementRes>> {
        val bookForSaleList: List<BookForSale> = if(category != null){
            bookForSaleRepository.findByCategoryAndSalePriceGreaterThan(category, 0, PageRequest.of(0, mainPageElementSize))
        } else{
            bookForSaleRepository.findBySalePriceGreaterThanOrderByIdDesc(0, PageRequest.of(0, mainPageElementSize))
        }
        return ResponseEntity(
            bookForSaleList.map {bookForSale ->  BookForSaleGetElementRes(bookForSale) }, HttpStatus.OK
        )
    }
}