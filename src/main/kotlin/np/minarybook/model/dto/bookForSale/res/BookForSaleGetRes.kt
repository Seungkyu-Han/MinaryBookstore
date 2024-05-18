package np.minarybook.model.dto.bookForSale.res

import np.minarybook.model.dto.book.res.BookGetRes
import np.minarybook.model.entity.BookForSale

data class BookForSaleGetRes(
    val id: Int,
    val bookGetRes: BookGetRes,
    val conditions: List<Short>,
    val imageList: List<String>,
    val detail: String,
    val salePrice: Int,
    val category: String,
    val longitude: Float,
    val latitude: Float
){
    constructor(bookForSale: BookForSale, imageList: List<String>): this(
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
        latitude = bookForSale.latitude
    )
}
