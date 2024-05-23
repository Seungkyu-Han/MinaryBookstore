package np.minarybook.model.dto.bookForRent.res

import np.minarybook.model.entity.BookForRent
import np.minarybook.model.enum.Category
import np.minarybook.model.enum.State
import np.minarybook.utilization.StringUtil
import java.time.LocalDate

data class BookForRentGetElementRes(
    val id: Int,
    val title: String,
    val author: List<String>,
    val img: String,
    val price: Int,
    val salePrice: Int,
    val category: Category,
    val publisher: String,
    val publicationDate: String,
    val isSave: Boolean,
    val state: State,
    val createdAt: LocalDate
){
    constructor(bookForRent: BookForRent): this(
        id = bookForRent.id ?: 0,
        title = bookForRent.book.title ?: "",
        author = StringUtil().stringToList(bookForRent.book.author ?: ""),
        img = bookForRent.book.image ?: "",
        price = bookForRent.book.price ?: 0,
        salePrice = bookForRent.salePrice,
        category = bookForRent.category,
        publisher = bookForRent.book.publisher ?: "",
        publicationDate = bookForRent.book.publicationDate ?: "",
        isSave = false,
        state = bookForRent.state,
        createdAt = bookForRent.createdAt
    )
}