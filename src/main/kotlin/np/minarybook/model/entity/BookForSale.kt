package np.minarybook.model.entity

import jakarta.persistence.*
import np.minarybook.model.dto.bookForSale.req.BookForSalePostReq
import np.minarybook.model.enum.State

@Entity
data class BookForSale(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @ManyToOne
    var book: Book,

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

    val category: String,

    val longitude: Float,

    val latitude: Float
){
    constructor(bookForSalePostReq: BookForSalePostReq, book: Book): this(
        id = null,
        book = book,
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
        latitude = bookForSalePostReq.latitude
    )
}
