package np.minarybook.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import np.minarybook.application.BookForSaleService
import np.minarybook.model.dto.bookForSale.res.BookForSaleGetRes
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/bookForSale")
@Tag(name="Book For Sale{개발 중}")
class BookForSaleController(private val bookForSaleService: BookForSaleService) {

    @GetMapping
    @Operation(summary = "판매 책 조회 API")
    fun get(@RequestParam id: Int): ResponseEntity<BookForSaleGetRes> {
        return bookForSaleService.get(id)
    }
}