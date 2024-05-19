package np.minarybook.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import np.minarybook.application.BookForSaleService
import np.minarybook.model.dto.bookForSale.req.BookForSalePostReq
import np.minarybook.model.dto.bookForSale.res.BookForSaleGetElementRes
import np.minarybook.model.dto.bookForSale.res.BookForSaleGetRes
import np.minarybook.model.enum.Category
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
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
    @Operation(summary = "판매 책 조회 API")
    fun get(@RequestParam id: Int): ResponseEntity<BookForSaleGetRes> {
        return bookForSaleService.get(id)
    }

    @PostMapping
    @Operation(summary = "판매 책 업로드")
    fun get(@RequestBody bookForSalePostReq: BookForSalePostReq, @Parameter(hidden = true) authentication: Authentication): ResponseEntity<HttpStatus>{
        return bookForSaleService.post(bookForSalePostReq, authentication)
    }

    @DeleteMapping
    @Operation(summary = "판매 책 글 삭제 API")
    fun delete(@RequestParam id: Int, @Parameter(hidden = true) authentication: Authentication): ResponseEntity<HttpStatus>{
        return bookForSaleService.delete(id, authentication)
    }

    @GetMapping("/list")
    @Operation()
    fun getList(@RequestParam category: Category?, @Parameter(hidden = true) authentication: Authentication): ResponseEntity<List<BookForSaleGetElementRes>>{
        return bookForSaleService.getList(category, authentication)
    }
}