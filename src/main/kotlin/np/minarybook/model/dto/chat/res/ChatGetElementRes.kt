package np.minarybook.model.dto.chat.res

import java.time.LocalDateTime

data class ChatGetElementRes(
    val isUser: Boolean,
    val localDateTime: LocalDateTime,
    val content: String
)
