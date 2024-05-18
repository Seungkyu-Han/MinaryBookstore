package np.minarybook.model.dto.bookForSale.res

import np.minarybook.model.entity.BookForSale

data class BookForSaleGetElementRes(
    val id: Int,
    val title: String,
    val author: String,
    val price: Int,
    val salePrice: Int,
    val category: String,
    val publisher: String,
    val publicationDate: String,
    val isSave: Boolean
){
    constructor(bookForSale: BookForSale): this(
        id = bookForSale.id ?: 0,
        title = bookForSale.book.title ?: "",
        author = bookForSale.book.author ?: "",
        price = bookForSale.book.price ?: 0,
        salePrice = bookForSale.salePrice,
        category = bookForSale.category,
        publisher = bookForSale.book.publisher ?: "",
        publicationDate = bookForSale.book.publicationDate ?: "",
        isSave = false
    )
}
