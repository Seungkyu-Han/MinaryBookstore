package np.minarybook.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import np.minarybook.application.ImageService
import np.minarybook.model.dto.image.res.ImagePostRes
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/image")
@Tag(name="Image")
class ImageController(private val imageService: ImageService) {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(summary = "이미지를 업로드하고 이미지 ID를 반환")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf(Content(schema = Schema(implementation = ImagePostRes::class))))
    )
    fun postImage(@RequestPart multipartFileList: List<MultipartFile>, @Parameter(hidden = true) authentication: Authentication): ResponseEntity<ImagePostRes>{
        return imageService.post(multipartFileList, authentication)
    }

    @DeleteMapping
    @Operation(summary = "사진을 삭제")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "성공", content = arrayOf())
    )
    fun deleteImage(@RequestParam imageIdList: List<Int>, @Parameter(hidden = true) authentication: Authentication): ResponseEntity<HttpStatus>{
        return imageService.delete(imageIdList, authentication)
    }
}