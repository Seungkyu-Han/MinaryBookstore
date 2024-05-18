package np.minarybook.application

import np.minarybook.model.dto.image.res.ImagePostRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.multipart.MultipartFile

interface ImageService {
    fun post(multipartFileList: List<MultipartFile>, authentication: Authentication): ResponseEntity<ImagePostRes>
    fun delete(imageIdList: List<Int>, authentication: Authentication): ResponseEntity<HttpStatus>
}