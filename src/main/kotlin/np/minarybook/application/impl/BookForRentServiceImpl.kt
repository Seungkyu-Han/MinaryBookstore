package np.minarybook.application.impl

import np.minarybook.application.BookForRentService
import np.minarybook.model.dto.bookForRent.req.BookForRentPostReq
import np.minarybook.model.dto.bookForRent.req.BookForRentPutReq
import np.minarybook.model.dto.bookForRent.res.BookForRentGetElementRes
import np.minarybook.model.dto.bookForRent.res.BookForRentGetRes
import np.minarybook.model.entity.Book
import np.minarybook.model.entity.BookForRent
import np.minarybook.model.entity.BookForRentSave
import np.minarybook.model.entity.User
import np.minarybook.model.enum.Category
import np.minarybook.model.enum.State
import np.minarybook.repository.BookForRentRepository
import np.minarybook.repository.BookForRentSaveRepository
import np.minarybook.repository.ImageRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class BookForRentServiceImpl(
    private val bookForRentRepository: BookForRentRepository,
    private val imageRepository: ImageRepository,
    private val bookForRentSaveRepository: BookForRentSaveRepository,
    @Value("\${mainPageElementSize}")
    private val mainPageElementSize: Int) : BookForRentService{
    override fun get(id: Int, authentication: Authentication?): ResponseEntity<BookForRentGetRes> {

        val bookForRent = bookForRentRepository.findById(id).orElseThrow{NullPointerException()}

        val image = imageRepository.findByBookForRent(bookForRent).map { image -> image.url }

        println(image)

        return ResponseEntity.ok(BookForRentGetRes(bookForRent, image,
            authentication?.name?.toLong() == bookForRent.user.id
        ))
    }

    override fun put(bookForRentPutReq: BookForRentPutReq, authentication: Authentication): ResponseEntity<HttpStatus> {
        val bookForRent = bookForRentRepository.findById(bookForRentPutReq.id).orElseThrow{NullPointerException()}
        bookForRent.put(bookForRentPutReq)
        bookForRentRepository.save(bookForRent)
        return ResponseEntity.ok().build()
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
            imageRepository.save(image)
        }

        return ResponseEntity(HttpStatus.OK)
    }

    override fun patchSold(bookForRentId: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
        val bookForRent = bookForRentRepository.findById(bookForRentId).orElseThrow { NullPointerException() }
        if (bookForRent.user.id != authentication.name.toLong())
            return ResponseEntity(HttpStatus.FORBIDDEN)
        bookForRent.state = State.RENT
        bookForRentRepository.save(bookForRent)
        return ResponseEntity(HttpStatus.OK)
    }

    override fun postSave(bookForSaleId: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
        val bookForRent = bookForRentRepository.findById(bookForSaleId).orElseThrow{NullPointerException()}
        bookForRentSaveRepository.save(BookForRentSave(User(authentication.name.toLong()), bookForRent))
        return ResponseEntity(HttpStatus.OK)
    }

    override fun deleteSave(bookForSaleId: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
        val bookForRent = bookForRentRepository.findById(bookForSaleId).orElseThrow{NullPointerException()}
        bookForRentSaveRepository.deleteByUserAndBookForRent(User(authentication.name.toLong()), bookForRent)
        return ResponseEntity(HttpStatus.OK)
    }

    override fun delete(id: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
        bookForRentRepository.deleteByIdAndUser(id, User(authentication.name.toLong()))
        return ResponseEntity(HttpStatus.OK)
    }

    override fun getList(
        category: Category?,
        authentication: Authentication?
    ): ResponseEntity<List<BookForRentGetElementRes>> {
        val bookForRentList: List<BookForRent> = if(category != null){
            bookForRentRepository.findByCategoryOrderByIdDesc(category, PageRequest.of(0, mainPageElementSize))
        }else{
            bookForRentRepository.findByOrderByIdDesc(PageRequest.of(0, mainPageElementSize))
        }
        return ResponseEntity(
            bookForRentList.map {bookForRent ->  BookForRentGetElementRes(bookForRent) }, HttpStatus.OK
        )
    }

    override fun getSearchTitle(
        title: String,
        authentication: Authentication?
    ): ResponseEntity<List<BookForRentGetElementRes>> {
        return ResponseEntity(bookForRentRepository.findByBookTitle("%${title}%").map{
                bookForRent -> BookForRentGetElementRes(bookForRent)
        }, HttpStatus.OK)
    }

    override fun getSearchIsbn(
        isbn: String,
        authentication: Authentication?
    ): ResponseEntity<List<BookForRentGetElementRes>> {
        return ResponseEntity(bookForRentRepository.findByBookIsbn(isbn).map{
                bookForRent -> BookForRentGetElementRes((bookForRent))
        }, HttpStatus.OK)
    }
}