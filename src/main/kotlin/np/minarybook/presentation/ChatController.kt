package np.minarybook.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import np.minarybook.application.ChatService
import np.minarybook.model.dto.chat.res.ChatGetElementRes
import np.minarybook.model.dto.chat.res.ChatListElementRes
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
@Tag(name="chat")
class ChatController(private val chatService: ChatService) {

    @GetMapping
    @Operation(summary = "해당 유저와 했던 채팅 기록 조회")
    fun get(@RequestParam userId: Long, @Parameter(hidden = true) authentication: Authentication): ResponseEntity<List<ChatGetElementRes>>{
        return chatService.get(userId, authentication)
    }

    @GetMapping("/list")
    @Operation(summary = "채팅방 목록 조회")
    fun getList(@Parameter(hidden = true) authentication: Authentication): ResponseEntity<List<ChatListElementRes>>{
        return chatService.getList(authentication)
    }
}