package np.minarybook.repository

import np.minarybook.model.entity.Book
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BookRepository: JpaRepository<Book, Long> {

    fun findByIsbnAndFind(isbn: String, find: Boolean): Optional<Book>

    fun findTopByTitle(title: String): Optional<Book>
}