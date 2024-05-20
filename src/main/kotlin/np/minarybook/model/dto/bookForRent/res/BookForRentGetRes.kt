package np.minarybook.model.dto.bookForRent.res

import np.minarybook.model.dto.book.res.BookGetRes
import np.minarybook.model.entity.BookForRent
import np.minarybook.model.enum.Category
import java.time.LocalDate

data class BookForRentGetRes(
    val id: Int,
    val bookGetRes: BookGetRes,
    val conditions: List<Short>,
    val imageList: List<String>,
    val detail: String,
    val salePrice: Int,
    val category: Category,
    val longitude: Float,
    val latitude: Float,
    val address: String,
    val startDate: LocalDate,
    val endDate: LocalDate
){
    constructor(bookForRent: BookForRent, imageList: List<String>): this(
        id = bookForRent.id ?: 0,
        bookGetRes = BookGetRes(bookForRent.book),
        imageList = imageList,
        conditions = listOf(
            bookForRent.isUnderline, bookForRent.isWriting, bookForRent.isClean, bookForRent.isName, bookForRent.isDiscoloration, bookForRent.isDamaged
        ),
        detail = bookForRent.detail,
        salePrice = bookForRent.salePrice,
        category = bookForRent.category,
        longitude = bookForRent.longitude,
        latitude = bookForRent.latitude,
        address = bookForRent.address,
        startDate = bookForRent.startDate,
        endDate = bookForRent.endDate
    )
}
