package np.minarybook.application.impl

import np.minarybook.application.ChatService
import np.minarybook.model.dto.chat.res.ChatGetElementRes
import np.minarybook.repository.ChatRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class ChatServiceImpl(private val chatRepository: ChatRepository) : ChatService{

    override fun get(userId: Long, authentication: Authentication): ResponseEntity<List<ChatGetElementRes>> {

        val userOne = authentication.name.toLong()

        return ResponseEntity(chatRepository.findByMyChat(userOne, userId).map{
                chat ->
            ChatGetElementRes(chat.sender.id == userOne, chat.dateTime, chat.content)
        }, HttpStatus.OK)
    }
}