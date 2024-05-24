package np.minarybook.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
data class BookForSaleSave(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @ManyToOne
    val user: User,

    @ManyToOne
    val bookForSale: BookForSale
){
    constructor(user: User, bookForSale: BookForSale): this(id = null, user = user, bookForSale = bookForSale)
}
