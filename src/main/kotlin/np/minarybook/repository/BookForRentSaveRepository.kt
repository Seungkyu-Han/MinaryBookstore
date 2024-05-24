package np.minarybook.repository

import np.minarybook.model.entity.BookForRent
import np.minarybook.model.entity.BookForRentSave
import np.minarybook.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface BookForRentSaveRepository: JpaRepository<BookForRentSave, Int> {
    fun deleteByUserAndBookForRent(user: User, bookForRent: BookForRent)
}