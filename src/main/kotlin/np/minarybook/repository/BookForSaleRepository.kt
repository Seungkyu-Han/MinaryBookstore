package np.minarybook.repository

import np.minarybook.model.entity.BookForSale
import org.springframework.data.jpa.repository.JpaRepository

interface BookForSaleRepository: JpaRepository<BookForSale, Int> {
}