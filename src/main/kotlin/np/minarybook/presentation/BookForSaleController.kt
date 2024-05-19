package np.minarybook.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import np.minarybook.application.BookForSaleService
import np.minarybook.model.dto.bookForSale.req.BookForSalePostReq
import np.minarybook.model.dto.bookForSale.req.BookForSalePutReq
import np.minarybook.model.dto.bookForSale.res.BookForSaleGetElementRes
import np.minarybook.model.dto.bookForSale.res.BookForSaleGetRes
import np.minarybook.model.enum.Category
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
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

    @PutMapping
    @Operation(summary = "판매 책 글 수정 API")
    fun put(@RequestBody bookForSalePutReq: BookForSalePutReq, @Parameter(hidden = true) authentication: Authentication):ResponseEntity<HttpStatus>{
        return bookForSaleService.put(bookForSalePutReq, authentication)
    }

    @PatchMapping("/sold")
    @Operation(summary = "책 판매 완료")
    fun patchSold(@RequestParam bookForSaleId: Int, @Parameter(hidden = true) authentication: Authentication):ResponseEntity<HttpStatus>{
        return bookForSaleService.patchSold(bookForSaleId, authentication)
    }
    @GetMapping("/list")
    @Operation(summary = "각 카테고리별 조회", description = "null이면 전체로 조회")
    fun getList(@RequestParam category: Category?, @Parameter(hidden = true) authentication: Authentication): ResponseEntity<List<BookForSaleGetElementRes>>{
        return bookForSaleService.getList(category, authentication)
    }

    @GetMapping("/share-list")
    @Operation(summary = "각 카테고리별 조회, 가격이 0원인거 조회", description = "null이면 전체로 조회")
    fun getShareList(@RequestParam category: Category?, @Parameter(hidden = true) authentication: Authentication): ResponseEntity<List<BookForSaleGetElementRes>>{
        return bookForSaleService.getShareList(category, authentication)
    }
}