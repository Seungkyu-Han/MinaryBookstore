package np.minarybook.repository

import np.minarybook.model.entity.BookForSale
import np.minarybook.model.enum.Category
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BookForSaleRepository: JpaRepository<BookForSale, Int> {
    fun findByCategoryAndSalePriceGreaterThan(category: Category, salePrice: Int, pageable: Pageable): List<BookForSale>

    fun findBySalePriceGreaterThanOrderByIdDesc(salePrice: Int, pageable: Pageable): List<BookForSale>
}