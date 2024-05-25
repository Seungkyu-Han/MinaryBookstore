package np.minarybook.repository

import np.minarybook.model.entity.BookForRent
import np.minarybook.model.entity.User
import np.minarybook.model.enum.Category
import np.minarybook.model.enum.State
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface BookForRentRepository: JpaRepository<BookForRent, Int> {
    fun findByCategoryOrderByIdDesc(category: Category, pageable: Pageable): List<BookForRent>

    @Transactional
    fun deleteByIdAndUser(id: Int, user: User)
    fun findByOrderByIdDesc(pageable: Pageable): List<BookForRent>

    @Query("SELECT bookForRent FROM BookForRent bookForRent " +
            "LEFT JOIN bookForRent.book book " +
            "WHERE book.title LIKE (:title) AND bookForRent.state = :state")
    fun findByBookTitle(title: String, state: State = State.SALE): List<BookForRent>

    @Query("SELECT bookForRent FROM BookForRent bookForRent " +
            "LEFT JOIN bookForRent.book book " +
            "WHERE book.isbn LIKE (:isbn) AND bookForRent.state = :state"
    )
    fun findByBookIsbn(isbn: String, state: State = State.SALE): List<BookForRent>
}