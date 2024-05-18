package np.minarybook.model.dto.bookForSale.req

data class BookForSalePostReq(
    val bookId: Long,
    val imageIdList: List<Int>,

    var underline: Short,

    var writing: Short,

    var clean: Short,

    var name: Short,

    var discoloration: Short,

    var damaged: Short,

    var detail: String,

    var salePrice: Int,

    val category: String,

    val longitude: Float,

    val latitude: Float
)