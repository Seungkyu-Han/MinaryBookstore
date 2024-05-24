package np.minarybook.repository

import np.minarybook.model.entity.BookForSale
import np.minarybook.model.entity.BookForSaleSave
import np.minarybook.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface BookForSaleSaveRepository: JpaRepository<BookForSaleSave, Int> {
    fun deleteByUserAndBookForSale(user: User, bookForSale: BookForSale)
    fun existsByUserAndBookForSale(user: User, bookForSale: BookForSale): Boolean
}