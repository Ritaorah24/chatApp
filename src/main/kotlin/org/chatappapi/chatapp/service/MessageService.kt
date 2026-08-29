package org.chatappapi.chatapp.service

import org.chatappapi.chatapp.model.Message
import org.chatappapi.chatapp.repository.MessageRepository
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val messageRepository: MessageRepository
) {
    fun saveMessage(message: Message){
        messageRepository.save(message)
    }
    fun getMessages(roomName: String) : List<Message> {
      return  messageRepository.findByRoom(roomName)
    }
}