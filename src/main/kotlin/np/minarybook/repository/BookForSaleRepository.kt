package np.minarybook.repository

import np.minarybook.model.entity.BookForSale
import np.minarybook.model.enum.Category
import np.minarybook.model.enum.State
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BookForSaleRepository: JpaRepository<BookForSale, Int> {
    fun findByCategoryAndStateAndSalePriceGreaterThanOrderByIdDesc(category: Category, state: State, salePrice: Int, pageable: Pageable): List<BookForSale>

    fun findByStateAndSalePriceGreaterThanOrderByIdDesc(state: State, salePrice: Int, pageable: Pageable): List<BookForSale>

    fun findByCategoryAndStateAndSalePriceOrderByIdDesc(category: Category, state:State, salePrice: Int, pageable: Pageable): List<BookForSale>

    fun findByStateAndSalePriceOrderByIdDesc(state: State, salePrice: Int, pageable: Pageable): List<BookForSale>

    @Query("SELECT bookForSale FROM BookForSale bookForSale " +
            "LEFT JOIN bookForSale.book book " +
            "WHERE book.title LIKE(:title) AND bookForSale.state = :state")
    fun findByBookTitle(title: String, state: State = State.SALE): List<BookForSale>

    @Query("SELECT bookForSale FROM BookForSale bookForSale " +
            "LEFT JOIN bookForSale.book book " +
            "WHERE book.isbn LIKE (:isbn) AND bookForSale.state = :state")
    fun findByBookIsbn(isbn: String, state: State = State.SALE): List<BookForSale>
}