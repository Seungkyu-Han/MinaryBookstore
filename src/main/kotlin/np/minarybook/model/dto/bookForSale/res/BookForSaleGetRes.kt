package np.minarybook.model.dto.bookForSale.res

import np.minarybook.model.dto.book.res.BookGetRes
import np.minarybook.model.entity.BookForSale
import np.minarybook.model.enum.Category
import np.minarybook.model.enum.State
import java.time.LocalDate

data class BookForSaleGetRes(
    val id: Int,
    val bookGetRes: BookGetRes,
    val conditions: List<Short>,
    val imageList: List<String>,
    val detail: String,
    val salePrice: Int,
    val category: Category,
    val longitude: Float,
    val latitude: Float,
    val address: String?,
    val editable: Boolean,
    val state: State,
    val createdAt: LocalDate,
    val isSave: Boolean
){
    constructor(bookForSale: BookForSale, imageList: List<String>, editable: Boolean, isSave: Boolean): this(
        id = bookForSale.id ?: 0,
        bookGetRes = BookGetRes(bookForSale.book),
        imageList = imageList,
        conditions = listOf(
            bookForSale.isUnderline, bookForSale.isWriting, bookForSale.isClean, bookForSale.isName, bookForSale.isDiscoloration, bookForSale.isDamaged
        ),
        detail = bookForSale.detail,
        salePrice = bookForSale.salePrice,
        category = bookForSale.category,
        longitude = bookForSale.longitude,
        latitude = bookForSale.latitude,
        address = bookForSale.address,
        editable = editable,
        state = bookForSale.state,
        createdAt = bookForSale.createdAt,
        isSave = isSave
    )
}
