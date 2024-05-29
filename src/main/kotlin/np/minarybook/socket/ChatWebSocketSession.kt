package np.minarybook.socket

import np.minarybook.model.entity.User
import org.springframework.web.socket.WebSocketSession


class ChatWebSocketSession(
    val webSocketSession: WebSocketSession,
    val receiver: User,
    val sender: User)
