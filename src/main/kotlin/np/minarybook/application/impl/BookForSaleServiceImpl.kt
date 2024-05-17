package np.minarybook.application.impl

import np.minarybook.application.BookForSaleService
import np.minarybook.model.dto.bookForSale.res.BookForSaleGetRes
import np.minarybook.repository.BookForSaleRepository
import np.minarybook.repository.BookRepository
import np.minarybook.repository.ImageRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.lang.NullPointerException

@Service
class BookForSaleServiceImpl(
    private val bookForSaleRepository: BookForSaleRepository,
    private val bookRepository: BookRepository,
    private val imageRepository: ImageRepository
) : BookForSaleService{

    override fun get(id: Int): ResponseEntity<BookForSaleGetRes> {

        val bookForSale = bookForSaleRepository.findById(id).orElseThrow{NullPointerException()}

        val image = imageRepository.findByBookForSale(bookForSale)

        return ResponseEntity.ok(
            BookForSaleGetRes(
                title = bookForSale.book.title,
                price = bookForSale.book.price,
                author = bookForSale.book.author,
                state = bookForSale.state.name,
                image = image.map { imageElement -> imageElement.url },
                publicationDate = bookForSale.book.publicationDate ?: "",
                publisher = bookForSale.book.publisher ?: "",
                salePrice = bookForSale.salePrice,
                category = bookForSale.category,
                isbn = bookForSale.book.isbn ?: "",
                longitude = bookForSale.longitude,
                latitude = bookForSale.latitude
            )
        )
    }
}