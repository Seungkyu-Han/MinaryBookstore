package np.minarybook.application.impl

import np.minarybook.application.ChatService
import np.minarybook.model.dto.chat.res.ChatGetElementRes
import np.minarybook.model.dto.chat.res.ChatListElementRes
import np.minarybook.model.entity.User
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

    override fun getList(authentication: Authentication): ResponseEntity<List<ChatListElementRes>> {
        val user = User(authentication.name.toLong())

        val result = ArrayList<ChatListElementRes>()

        chatRepository.findByReceiverOrSenderOrderByIdDesc(user, user).map {
            chat ->
                var flag = true
                for(chatListElement in result){
                    if(chatListElement.id == chat.sender.id || chatListElement.id == chat.receiver.id){
                        flag = false
                        break
                    }
                }
                if(flag){
                    val resultId: Long
                    val resultImg: String
                    val resultName: String
                    if(chat.sender.id == user.id){
                        resultId = chat.receiver.id
                        resultImg = chat.receiver.profileImg ?: ""
                        resultName = chat.receiver.name ?: ""
                    }
                    else{
                        resultId = chat.sender.id
                        resultImg = chat.sender.profileImg ?: ""
                        resultName = chat.sender.name ?: ""
                    }

                    result.add(ChatListElementRes(resultId, resultName, resultImg, chat.content))
                }
        }
        return ResponseEntity.ok(result)
    }
}