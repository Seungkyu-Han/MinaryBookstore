package np.minarybook.application

import np.minarybook.model.dto.chat.res.ChatGetElementRes
import np.minarybook.model.dto.chat.res.ChatListElementRes
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication

interface ChatService {
    fun get(userId: Long, authentication: Authentication): ResponseEntity<List<ChatGetElementRes>>
    fun getList(authentication: Authentication): ResponseEntity<List<ChatListElementRes>>
}