package np.minarybook.repository

import np.minarybook.model.entity.BookForRent
import np.minarybook.model.entity.BookForSale
import np.minarybook.model.entity.Image
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository: JpaRepository<Image, Int> {

    fun findByBookForSale(bookForSale: BookForSale): List<Image>
    fun findByBookForRent(bookForRent: BookForRent): List<Image>
}