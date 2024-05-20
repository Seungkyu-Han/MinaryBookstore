package np.minarybook.model.entity

import jakarta.persistence.*
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
)
