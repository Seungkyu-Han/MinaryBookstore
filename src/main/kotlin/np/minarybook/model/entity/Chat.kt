package np.minarybook.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
data class Chat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    val dateTime: LocalDateTime,

    @ManyToOne
    val sender: User,

    @ManyToOne
    val receiver: User,

    val content: String
){
    constructor(sender: User, receiver: User, content: String): this(
        id = null, dateTime = LocalDateTime.now(), sender = sender, receiver = receiver, content = content)
}
