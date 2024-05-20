package np.minarybook.application

import np.minarybook.model.dto.bookForRent.req.BookForRentPostReq
import np.minarybook.model.dto.bookForRent.res.BookForRentGetRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication

interface BookForRentService {
    fun get(id: Int): ResponseEntity<BookForRentGetRes>
    fun post(bookForRentPostReq: BookForRentPostReq, authentication: Authentication): ResponseEntity<HttpStatus>
}