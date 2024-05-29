package np.minarybook.repository

import np.minarybook.model.entity.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChatRepository: JpaRepository<Chat, Int> {

    @Query(
        "SELECT chat FROM Chat chat " +
                "WHERE (chat.receiver.id = :userOne AND chat.sender.id = :userTwo) OR (chat.receiver.id = :userTwo AND chat.sender.id = :userOne)"
    )
    fun findByMyChat(userOne: Long, userTwo: Long): List<Chat>
}