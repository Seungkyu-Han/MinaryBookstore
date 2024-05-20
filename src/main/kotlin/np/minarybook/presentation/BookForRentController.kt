package np.minarybook.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import np.minarybook.application.BookForRentService
import np.minarybook.model.dto.bookForRent.req.BookForRentPostReq
import np.minarybook.model.dto.bookForRent.res.BookForRentGetRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/bookForRent")
@Tag(name = "Book For Rent")
class BookForRentController(private val bookForRentService: BookForRentService) {

    @GetMapping
    @Operation(summary = "대여 책 조회 API")
    fun get(@RequestParam id: Int): ResponseEntity<BookForRentGetRes>{
        return bookForRentService.get(id)
    }

    @PostMapping
    @Operation(summary = "판매 책 업로드")
    fun post(@RequestBody bookForRentPostReq: BookForRentPostReq, @Parameter(hidden = true) authentication: Authentication): ResponseEntity<HttpStatus>{
        return bookForRentService.post(bookForRentPostReq, authentication)
    }
}