package np.minarybook.model.dto.book.res

import np.minarybook.model.entity.Book

data class BookGetBestRes(
    val book: Book,
    val count: Int
)
