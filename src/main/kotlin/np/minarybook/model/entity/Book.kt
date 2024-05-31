package np.minarybook.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import np.minarybook.model.dto.book.req.BookPostReq

@Entity
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    var title: String?,

    var price: Int?,

    val author: String?,

    val publisher: String?,

    val image: String?,

    val publicationDate: String?,

    val isbn: String?,

    val find: Boolean
){
    constructor(bookPostReq: BookPostReq): this(
        id = null, title = bookPostReq.title, price = bookPostReq.price, author = bookPostReq.author, publisher = bookPostReq.publisher,
        image = null, publicationDate = bookPostReq.publicationDate, isbn = bookPostReq.isbn, find = false
    )

    constructor(id: Long): this(
        id = id, title = null, price = null, author = null, publisher = null, image = null, publicationDate = null, isbn = null, find = false
    )
}