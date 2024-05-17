package np.minarybook.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import np.minarybook.application.BookService
import np.minarybook.model.dto.auth.res.AuthLoginRes
import np.minarybook.model.dto.book.req.BookPostReq
import np.minarybook.model.dto.book.res.BookGetRes
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/book")
@Tag(name="Book")
class BookController(private val bookService: BookService) {

    @GetMapping("/isbn")
    @Operation(summary = "Get Book Info By ISBN")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content(schema = Schema(implementation = BookGetRes::class))))
    )
    fun getIsbn(@Parameter isbn: String): ResponseEntity<BookGetRes> {
        return bookService.getIsbn(isbn)
    }

    @GetMapping("/title")
    @Operation(summary = "Get Book Info By Title")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content(schema = Schema(implementation = BookGetRes::class))))
    )
    fun getTitle(@Parameter title: String): ResponseEntity<BookGetRes>{
        return bookService.getTitle(title)
    }

    @PostMapping
    @Operation(summary = "POST Book Info")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content(schema = Schema(implementation = BookGetRes::class))))
    )
    fun post(@RequestBody bookPostReq: BookPostReq): ResponseEntity<BookGetRes>{
        return bookService.post(bookPostReq)
    }
}