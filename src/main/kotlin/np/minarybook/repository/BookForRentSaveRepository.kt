package np.minarybook.repository

import np.minarybook.model.entity.BookForRent
import np.minarybook.model.entity.BookForRentSave
import np.minarybook.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

interface BookForRentSaveRepository: JpaRepository<BookForRentSave, Int> {
    @Transactional
    fun deleteByUserAndBookForRent(user: User, bookForRent: BookForRent)
    fun existsByUserAndBookForRent(user: User, bookForRent: BookForRent?): Boolean
    fun findByUser(user: User): List<BookForRentSave>
}