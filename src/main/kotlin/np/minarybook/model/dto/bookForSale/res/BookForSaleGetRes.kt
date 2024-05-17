package np.minarybook.model.dto.bookForSale.res

data class BookForSaleGetRes(
    val title: String,
    val price: Int?,
    val author: String?,
    val state: String,
    val image: List<String>,
    val publicationDate: String,
    val publisher: String,
    val salePrice: Int,
    val category: String,
    val isbn: String,
    val longitude: Float,
    val latitude: Float
)
