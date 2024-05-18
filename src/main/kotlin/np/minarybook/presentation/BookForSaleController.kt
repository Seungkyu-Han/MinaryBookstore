package np.minarybook.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import np.minarybook.application.BookForSaleService
import np.minarybook.model.dto.bookForSale.req.BookForSalePostReq
import np.minarybook.model.dto.bookForSale.res.BookForSaleGetElementRes
import np.minarybook.model.dto.bookForSale.res.BookForSaleGetRes
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
@RequestMapping("/api/bookForSale")
@Tag(name="Book For Sale")
class BookForSaleController(private val bookForSaleService: BookForSaleService) {

    @GetMapping
    @Operation(summary = "판매 책 조회 API{개발 중}")
    fun get(@RequestParam id: Int): ResponseEntity<BookForSaleGetRes> {
        return bookForSaleService.get(id)
    }

    @PostMapping
    @Operation(summary = "판매 책 업로드")
    fun get(@RequestBody bookForSalePostReq: BookForSalePostReq, @Parameter(hidden = true) authentication: Authentication): ResponseEntity<HttpStatus>{
        return bookForSaleService.post(bookForSalePostReq, authentication)
    }

    @GetMapping("/list")
    @Operation()
    fun getList(@RequestParam category: String?, @Parameter(hidden = true) authentication: Authentication): ResponseEntity<List<BookForSaleGetElementRes>>{
        return bookForSaleService.getList(category, authentication)
    }
}