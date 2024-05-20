package np.minarybook.application.impl

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import np.minarybook.application.ImageService
import np.minarybook.model.dto.image.res.ImagePostRes
import np.minarybook.model.entity.Image
import np.minarybook.repository.ImageRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class ImageServiceImpl(
    private val imageRepository: ImageRepository,
    private val amazonS3Client: AmazonS3Client,
    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String,
    @Value("\${cloud.aws.s3.imagePath}")
    private val profileImagePath: String,
    @Value("\${cloud.aws.s3.s3BucketPrefix}")
    private val s3BucketPrefix: String
):ImageService{

    override fun post(
        multipartFileList: List<MultipartFile>,
        authentication: Authentication
    ): ResponseEntity<ImagePostRes> {
        return ResponseEntity(ImagePostRes(multipartFileList.map {
                multipartFile ->
            val image = Image(id = null, bookForSale = null, bookForRent = null, url = "${s3BucketPrefix}${uploadImageToS3(multipartFile)}")
            imageRepository.save(image)
            image.id ?: throw NullPointerException() }), HttpStatus.OK)
    }

    override fun delete(imageIdList: List<Int>, authentication: Authentication): ResponseEntity<HttpStatus> {
        imageRepository.deleteAllById(imageIdList)
        return ResponseEntity(HttpStatus.OK)
    }

    private fun uploadImageToS3(multipartFile: MultipartFile): String{
        val uuidName = profileImagePath + UUID.randomUUID() + "." + StringUtils.getFilenameExtension(multipartFile.originalFilename)

        val metadata = ObjectMetadata()

        metadata.contentType = multipartFile.contentType
        metadata.contentLength = multipartFile.size

        amazonS3Client.putObject(bucket, uuidName, multipartFile.inputStream, metadata)
        return uuidName
    }
}