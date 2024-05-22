package np.minarybook.application

import np.minarybook.model.dto.bookForRent.req.BookForRentPostReq
import np.minarybook.model.dto.bookForRent.req.BookForRentPutReq
import np.minarybook.model.dto.bookForRent.res.BookForRentGetElementRes
import np.minarybook.model.dto.bookForRent.res.BookForRentGetRes
import np.minarybook.model.enum.Category
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication

interface BookForRentService {
    fun get(id: Int): ResponseEntity<BookForRentGetRes>
    fun post(bookForRentPostReq: BookForRentPostReq, authentication: Authentication): ResponseEntity<HttpStatus>
    fun delete(id: Int, authentication: Authentication): ResponseEntity<HttpStatus>
    fun put(bookForRentPutReq: BookForRentPutReq, authentication: Authentication): ResponseEntity<HttpStatus>
    fun patchSold(bookForRentId: Int, authentication: Authentication): ResponseEntity<HttpStatus>
    fun getList(category: Category?, authentication: Authentication): ResponseEntity<List<BookForRentGetElementRes>>
}