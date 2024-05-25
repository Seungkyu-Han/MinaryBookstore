package np.minarybook.repository

import np.minarybook.model.entity.BookForSale
import np.minarybook.model.entity.User
import np.minarybook.model.enum.Category
import np.minarybook.model.enum.State
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BookForSaleRepository: JpaRepository<BookForSale, Int> {
    fun findByCategoryAndSalePriceGreaterThanOrderByIdDesc(category: Category, salePrice: Int, pageable: Pageable): List<BookForSale>

    fun findBySalePriceGreaterThanOrderByIdDesc(salePrice: Int, pageable: Pageable): List<BookForSale>

    fun findByCategoryAndSalePriceOrderByIdDesc(category: Category, salePrice: Int, pageable: Pageable): List<BookForSale>

    fun findBySalePriceOrderByIdDesc(salePrice: Int, pageable: Pageable): List<BookForSale>

    @Query("SELECT bookForSale FROM BookForSale bookForSale " +
            "LEFT JOIN bookForSale.book book " +
            "WHERE book.title LIKE(:title) AND bookForSale.state = :state")
    fun findByBookTitle(title: String, state: State = State.SALE): List<BookForSale>

    @Query("SELECT bookForSale FROM BookForSale bookForSale " +
            "LEFT JOIN bookForSale.book book " +
            "WHERE book.isbn LIKE (:isbn) AND bookForSale.state = :state")
    fun findByBookIsbn(isbn: String, state: State = State.SALE): List<BookForSale>
    fun findByUser(user: User): List<BookForSale>
}