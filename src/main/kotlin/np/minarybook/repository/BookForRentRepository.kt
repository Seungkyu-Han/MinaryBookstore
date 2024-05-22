package np.minarybook.repository

import np.minarybook.model.entity.BookForRent
import np.minarybook.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface BookForRentRepository: JpaRepository<BookForRent, Int> {
    fun deleteByIdAndUser(id: Int, user: User)
}