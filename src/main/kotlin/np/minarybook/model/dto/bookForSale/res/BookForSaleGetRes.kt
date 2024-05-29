package np.minarybook.model.dto.bookForSale.res

import np.minarybook.model.dto.book.res.BookGetRes
import np.minarybook.model.dto.image.res.ImageGetElementRes
import np.minarybook.model.entity.BookForSale
import np.minarybook.model.enum.Category
import np.minarybook.model.enum.State
import java.time.LocalDate

data class BookForSaleGetRes(
    val id: Int,
    val bookGetRes: BookGetRes,
    val conditions: List<Short>,
    val imageList: List<ImageGetElementRes>,
    val detail: String,
    val salePrice: Int,
    val category: Category,
    val longitude: Float,
    val latitude: Float,
    val address: String?,
    val editable: Boolean,
    val state: State,
    val createdAt: LocalDate,
    val isSave: Boolean,
    val writerName: String?,
    val writerImg: String?,
    val writerId: Long
){
    constructor(bookForSale: BookForSale, imageList: List<ImageGetElementRes>, editable: Boolean, isSave: Boolean): this(
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
        state = if(bookForSale.state == State.SOLD) State.SOLD else if(bookForSale.salePrice == 0) State.SHARED else State.SALE,
        createdAt = bookForSale.createdAt,
        isSave = isSave,
        writerName = bookForSale.user.name,
        writerImg = bookForSale.user.profileImg,
        writerId = bookForSale.user.id
    )
}
