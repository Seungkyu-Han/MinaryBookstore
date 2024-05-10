package np.minarybook.model.dto.isbn

data class ISBNGetRes(
    val title: String?,
    val price: Int?,
    val author: List<String>?,
    val img: String?,
    val publicationDate: String?,
    val publisher: String?,
    val isbn: String?
)
