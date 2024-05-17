package np.minarybook.application

import np.minarybook.model.dto.book.req.BookPostReq
import np.minarybook.model.dto.book.res.BookGetRes
import org.springframework.http.ResponseEntity

interface BookService {

    fun getIsbn(isbn: String): ResponseEntity<BookGetRes>
    fun getTitle(title: String): ResponseEntity<BookGetRes>
    fun post(bookPostReq: BookPostReq): ResponseEntity<BookGetRes>
}