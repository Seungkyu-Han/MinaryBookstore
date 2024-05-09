package np.minarybook.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import np.minarybook.application.ISBNService
import np.minarybook.model.dto.isbn.ISBNGetRes
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/isbn")
@Tag(name="ISBN")
class ISBNController(private val isbnService: ISBNService) {

    @GetMapping
    @Operation(summary = "Get Book Info By ISBN")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공")
    )
    fun get(@Parameter isbn: String): ResponseEntity<ISBNGetRes> {
        return isbnService.get(isbn)
    }
}