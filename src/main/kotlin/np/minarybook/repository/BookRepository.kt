package np.minarybook.repository

import np.minarybook.model.entity.Book
import np.minarybook.model.enum.State
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface BookRepository: JpaRepository<Book, Long> {

    fun findByIsbnAndFind(isbn: String, find: Boolean): Optional<Book>

    fun findTopByTitle(title: String): Optional<Book>

    @Query(
        "SELECT book FROM Book book " +
                "LEFT JOIN BookForSale bookForSale ON bookForSale.book = book " +
                "WHERE bookForSale.state = :state " +
                "GROUP BY book " +
                "ORDER BY COUNT(bookForSale) DESC"
    )
    fun findByBestSeller( state: State, pageable: Pageable): List<Book>


    @Query(
        "SELECT COUNT(book) FROM Book book " +
            "LEFT JOIN BookForSale bookForSale on bookForSale.book = book " +
            "WHERE book.id = :bookId AND bookForSale.state = :state"
    )
    fun countBestSeller(bookId: Long, state: State = State.SOLD): Int

    @Query(
        "SELECT book FROM Book book " +
                "LEFT JOIN BookForSale bookForSale ON bookForSale.book = book " +
                "WHERE bookForSale.state <> :state AND book.id NOT IN :excludedIds " +
                "GROUP BY book " +
                "ORDER BY COUNT(bookForSale) DESC"
    )
    fun findByNotBestSeller(state: State, pageable: Pageable, excludedIds: List<Long>): List<Book>

}