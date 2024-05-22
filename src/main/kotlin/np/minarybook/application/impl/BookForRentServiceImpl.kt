package np.minarybook.application.impl

import np.minarybook.application.BookForRentService
import np.minarybook.model.dto.bookForRent.req.BookForRentPostReq
import np.minarybook.model.dto.bookForRent.res.BookForRentGetRes
import np.minarybook.model.entity.Book
import np.minarybook.model.entity.BookForRent
import np.minarybook.model.entity.User
import np.minarybook.repository.BookForRentRepository
import np.minarybook.repository.ImageRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class BookForRentServiceImpl(
    private val bookForRentRepository: BookForRentRepository,
    private val imageRepository: ImageRepository,
    @Value("\${mainPageElementSize}")
    private val mainPageElementSize: Int) : BookForRentService{
    override fun get(id: Int): ResponseEntity<BookForRentGetRes> {

        val bookForRent = bookForRentRepository.findById(id).orElseThrow{NullPointerException()}

        val image = imageRepository.findByBookForRent(bookForRent).map { image -> image.url }

        return ResponseEntity.ok(BookForRentGetRes(bookForRent, image))
    }

    override fun post(
        bookForRentPostReq: BookForRentPostReq,
        authentication: Authentication
    ): ResponseEntity<HttpStatus> {
        val book = Book(bookForRentPostReq.bookId)

        val user = User(authentication.name.toLong())

        val bookForRent = BookForRent(bookForRentPostReq, book, user)

        bookForRentRepository.save(bookForRent)

        bookForRentPostReq.imageIdList.map {
                imageId ->
            val image = imageRepository.findById(imageId).orElseThrow{NullPointerException()}
            image.bookForRent = bookForRent
        }

        return ResponseEntity(HttpStatus.OK)
    }

    override fun delete(id: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
        bookForRentRepository.deleteByIdAndUser(id, User(authentication.name.toLong()))
        return ResponseEntity(HttpStatus.OK)
    }
}