package np.minarybook.model.entity

import jakarta.persistence.*
import np.minarybook.model.dto.bookForSale.req.BookForSalePostReq
import np.minarybook.model.dto.bookForSale.req.BookForSalePutReq
import np.minarybook.model.enum.Category
import np.minarybook.model.enum.State

@Entity
data class BookForSale(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @ManyToOne
    var book: Book,

    @ManyToOne
    var user: User,

    @Enumerated(EnumType.STRING)
    var state: State,

    var isUnderline: Short,

    var isWriting: Short,

    var isClean: Short,

    var isName: Short,

    var isDiscoloration: Short,

    var isDamaged: Short,

    var detail: String,

    var salePrice: Int,

    @Enumerated(EnumType.STRING)
    var category: Category,

    var longitude: Float,

    var latitude: Float,

    var address: String
){
    constructor(bookForSalePostReq: BookForSalePostReq, book: Book, user: User): this(
        id = null,
        book = book,
        user = user,
        state = State.SALE,
        isUnderline = bookForSalePostReq.conditions[0],
        isWriting = bookForSalePostReq.conditions[1],
        isClean = bookForSalePostReq.conditions[2],
        isName = bookForSalePostReq.conditions[3],
        isDiscoloration = bookForSalePostReq.conditions[4],
        isDamaged = bookForSalePostReq.conditions[5],
        detail = bookForSalePostReq.detail ?: "",
        salePrice = bookForSalePostReq.salePrice,
        category = bookForSalePostReq.category,
        longitude = bookForSalePostReq.longitude,
        latitude = bookForSalePostReq.latitude,
        address = bookForSalePostReq.address
    )

    fun put(bookForSalePutReq: BookForSalePutReq){
        isUnderline = bookForSalePutReq.conditions[0]
        isWriting = bookForSalePutReq.conditions[1]
        isClean = bookForSalePutReq.conditions[2]
        isName = bookForSalePutReq.conditions[3]
        isDiscoloration = bookForSalePutReq.conditions[4]
        isDamaged = bookForSalePutReq.conditions[5]
        detail = bookForSalePutReq.detail ?: ""
        salePrice = bookForSalePutReq.salePrice
        category = bookForSalePutReq.category
        longitude = bookForSalePutReq.longitude
        latitude = bookForSalePutReq.latitude
        address = bookForSalePutReq.address
    }
}
