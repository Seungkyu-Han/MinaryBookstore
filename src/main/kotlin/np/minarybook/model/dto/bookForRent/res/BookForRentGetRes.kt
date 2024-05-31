package np.minarybook.model.dto.bookForRent.res

import np.minarybook.model.dto.book.res.BookGetRes
import np.minarybook.model.dto.image.res.ImageGetElementRes
import np.minarybook.model.entity.BookForRent
import np.minarybook.model.enum.Category
import np.minarybook.model.enum.State
import java.time.LocalDate

data class BookForRentGetRes(
    val id: Int,
    val bookGetRes: BookGetRes,
    val conditions: List<Short>,
    val imageList: List<ImageGetElementRes>,
    val detail: String,
    val salePrice: Int,
    val category: Category,
    val longitude: Float,
    val latitude: Float,
    val address: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val editable: Boolean,
    val state: State,
    val createdAt: LocalDate,
    val isSave: Boolean,
    val writerName: String?,
    val writerImg: String?,
    val writerId: Long
){
    constructor(bookForRent: BookForRent, imageList: List<ImageGetElementRes>, editable: Boolean, isSave: Boolean): this(
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
        endDate = bookForRent.endDate,
        editable = editable,
        state = bookForRent.state,
        createdAt = bookForRent.createdAt,
        isSave = isSave,
        writerName = bookForRent.user.name,
        writerImg = bookForRent.user.profileImg,
        writerId = bookForRent.user.id
    )
}
