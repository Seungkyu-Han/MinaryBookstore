package np.minarybook.model.dto.isbn

data class ISBNSeojiRes(
    val TOTAL_COUNT: Int,
    val docs: List<ISBNBookRes>,
    val PAGE_NO: Int
)
