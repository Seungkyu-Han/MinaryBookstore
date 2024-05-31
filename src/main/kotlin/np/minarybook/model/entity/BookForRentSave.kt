package np.minarybook.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
data class BookForRentSave(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @ManyToOne
    val user: User,

    @ManyToOne
    val bookForRent: BookForRent
){
    constructor(user: User, bookForRent: BookForRent): this(id = null, user = user, bookForRent = bookForRent)
}
