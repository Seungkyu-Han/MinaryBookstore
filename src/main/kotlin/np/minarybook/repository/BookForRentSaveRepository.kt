package np.minarybook.repository

import np.minarybook.model.entity.BookForRentSave
import org.springframework.data.jpa.repository.JpaRepository

interface BookForRentSaveRepository: JpaRepository<BookForRentSave, Int> {
    fun deleteByUserAndBookForRent(bookForRentSave: BookForRentSave)
}