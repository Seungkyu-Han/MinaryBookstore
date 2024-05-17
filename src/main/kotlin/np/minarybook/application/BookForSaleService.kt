package np.minarybook.application

import np.minarybook.model.dto.bookForSale.res.BookForSaleGetRes
import org.springframework.http.ResponseEntity

interface BookForSaleService {
    fun get(id: Int): ResponseEntity<BookForSaleGetRes>
}