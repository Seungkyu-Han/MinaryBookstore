package np.minarybook.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class User(
    @Id
    var id: Long,

    @Column(length = 20)
    var name: String?,

    var profileImg: String?
){
    constructor(id: Long): this(id, null, null)
}
