package np.minarybook.model.dto.bookForSale.req

import np.minarybook.model.enum.Category

data class BookForSalePostReq(
    val bookId: Long,

    val imageIdList: List<Int>,

    var conditions: List<Short>,

    var detail: String?,

    var salePrice: Int,

    val category: Category,

    val longitude: Float,

    val latitude: Float,

    val address: String
)