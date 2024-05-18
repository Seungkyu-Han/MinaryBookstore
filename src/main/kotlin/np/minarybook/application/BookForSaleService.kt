package np.minarybook.application

import np.minarybook.model.dto.bookForSale.req.BookForSalePostReq
import np.minarybook.model.dto.bookForSale.res.BookForSaleGetElementRes
import np.minarybook.model.dto.bookForSale.res.BookForSaleGetRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication

interface BookForSaleService {
    fun get(id: Int): ResponseEntity<BookForSaleGetRes>
    fun post(bookForSalePostReq: BookForSalePostReq, authentication: Authentication): ResponseEntity<HttpStatus>
    fun getList(category: String?, authentication: Authentication): ResponseEntity<List<BookForSaleGetElementRes>>
}