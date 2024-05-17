package np.minarybook.model.entity

import jakarta.persistence.*
import np.minarybook.model.enum.State

@Entity
data class BookForSale(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val Id: Int,

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

    var Detail: String,

    var salePrice: Int,

    val category: String,

    val longitude: Float,

    val latitude: Float
)
