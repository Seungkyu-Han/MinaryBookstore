package np.minarybook.model.dto.bookForRent.req

import np.minarybook.model.enum.Category
import java.time.LocalDate

data class BookForRentPostReq(
    val bookId: Long,

    val imageIdList: List<Int>,

    var conditions: List<Short>,

    var detail: String?,

    var salePrice: Int,

    val category: Category,

    val longitude: Float,

    val latitude: Float,

    val address: String,

    val startDate: LocalDate,

    val endDate: LocalDate
)
