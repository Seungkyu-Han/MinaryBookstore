package np.minarybook.model.dto.bookForSale.res

import np.minarybook.model.entity.BookForSale
import np.minarybook.model.enum.Category
import np.minarybook.utilization.StringUtil

data class BookForSaleGetElementRes(
    val id: Int,
    val title: String,
    val author: List<String>,
    val img: String,
    val price: Int,
    val salePrice: Int,
    val category: Category,
    val publisher: String,
    val publicationDate: String,
    val isSave: Boolean
){
    constructor(bookForSale: BookForSale): this(
        id = bookForSale.id ?: 0,
        title = bookForSale.book.title ?: "",
        author =StringUtil().stringToList(bookForSale.book.author ?: ""),
        img = bookForSale.book.image ?: "",
        price = bookForSale.book.price ?: 0,
        salePrice = bookForSale.salePrice,
        category = bookForSale.category,
        publisher = bookForSale.book.publisher ?: "",
        publicationDate = bookForSale.book.publicationDate ?: "",
        isSave = false
    )
}
