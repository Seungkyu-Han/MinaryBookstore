package np.minarybook.application

import np.minarybook.model.dto.book.req.BookPostReq
import np.minarybook.model.dto.book.res.BookGetRes
import np.minarybook.model.dto.book.res.BookGetSaveRes
import np.minarybook.model.dto.book.res.BookGetUploadRes
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication

interface BookService {

    fun getIsbn(isbn: String): ResponseEntity<BookGetRes>
    fun getTitle(title: String): ResponseEntity<BookGetRes>
    fun post(bookPostReq: BookPostReq): ResponseEntity<BookGetRes>
    fun getSave(authentication: Authentication): ResponseEntity<BookGetSaveRes>
    fun getUpload(authentication: Authentication): ResponseEntity<BookGetUploadRes>
}