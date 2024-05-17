package np.minarybook.model.dto.book.res

import np.minarybook.model.entity.Book

data class BookGetRes(
    val id: Long,
    val title: String?,
    val price: Int?,
    val author: String?,
    val img: String?,
    val publicationDate: String?,
    val publisher: String?,
    val isbn: String?
){
    constructor(book: Book):this(
        id = book.id ?: throw NullPointerException(), title = book.title, price = book.price, author = book.author, img = book.image, publicationDate = book.publicationDate,
        publisher = book.publisher, isbn = book.isbn
    )
}
