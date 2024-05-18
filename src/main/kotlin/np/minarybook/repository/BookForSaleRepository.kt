package np.minarybook.repository

import np.minarybook.model.entity.BookForSale
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BookForSaleRepository: JpaRepository<BookForSale, Int> {

    fun findByCategoryOrderByIdDesc(category: String, pageable: Pageable): List<BookForSale>

    fun findByOrderByIdDesc(pageable: Pageable): List<BookForSale>
}