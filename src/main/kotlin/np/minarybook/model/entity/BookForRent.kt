package np.minarybook.model.entity

import jakarta.persistence.*
import np.minarybook.model.dto.bookForRent.req.BookForRentPostReq
import np.minarybook.model.enum.Category
import np.minarybook.model.enum.State
import java.time.LocalDate

@Entity
data class BookForRent(
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

    var startDate: LocalDate,

    var endDate: LocalDate,

    var longitude: Float,

    var latitude: Float,

    var address: String
){
    constructor(bookForRentPostReq: BookForRentPostReq, book: Book, user: User): this(
        id = null,
        book = book,
        user = user,
        state = State.SALE,
        isUnderline = bookForRentPostReq.conditions[0],
        isWriting = bookForRentPostReq.conditions[1],
        isClean = bookForRentPostReq.conditions[2],
        isName = bookForRentPostReq.conditions[3],
        isDiscoloration = bookForRentPostReq.conditions[4],
        isDamaged = bookForRentPostReq.conditions[5],
        detail = bookForRentPostReq.detail ?: "",
        salePrice = bookForRentPostReq.salePrice,
        category = bookForRentPostReq.category,
        longitude = bookForRentPostReq.longitude,
        latitude = bookForRentPostReq.latitude,
        address = bookForRentPostReq.address,
        startDate = bookForRentPostReq.startDate,
        endDate = bookForRentPostReq.endDate
    )
}
