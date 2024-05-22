package np.minarybook.repository

import np.minarybook.model.entity.BookForRent
import np.minarybook.model.entity.User
import np.minarybook.model.enum.Category
import np.minarybook.model.enum.State
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface BookForRentRepository: JpaRepository<BookForRent, Int> {
    fun findByCategoryAndStateOrderByIdDesc(category: Category, state: State, pageable: Pageable): List<BookForRent>

    fun deleteByIdAndUser(id: Int, user: User)
    fun findByStateOrderByIdDesc(sale: State, pageable: Pageable): List<BookForRent>
}