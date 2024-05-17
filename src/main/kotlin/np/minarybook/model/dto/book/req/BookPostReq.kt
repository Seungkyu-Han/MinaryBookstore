package np.minarybook.model.dto.book.req

data class BookPostReq(
    val title: String,
    val price: Int?,
    val author: String?,
    val publicationDate: String?,
    val publisher: String?,
    val isbn: String
)
