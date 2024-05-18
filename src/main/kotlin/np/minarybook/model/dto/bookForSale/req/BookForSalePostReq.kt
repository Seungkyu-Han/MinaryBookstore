package np.minarybook.model.dto.bookForSale.req

data class BookForSalePostReq(
    val bookId: Long,
    val imageIdList: List<Int>,

    var conditions: List<Short>,

    var detail: String?,

    var salePrice: Int,

    val category: String,

    val longitude: Float,

    val latitude: Float
)