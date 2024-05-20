package np.minarybook.repository

import np.minarybook.model.entity.BookForRent
import org.springframework.data.jpa.repository.JpaRepository

interface BookForRentRepository: JpaRepository<BookForRent, Int> {
}