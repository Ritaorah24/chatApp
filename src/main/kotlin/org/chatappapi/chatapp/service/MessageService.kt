package org.chatappapi.chatapp.service

import org.chatappapi.chatapp.model.Message
import org.chatappapi.chatapp.repository.MessageRepository
import org.springframework.stereotype.Service

@Service
class MessageService(
    private val messageRepository: MessageRepository
) {
    fun saveMessage(message: Message): Message {
        val id = messageRepository.save(message)

        return message.copy(id = id)
    }

    fun getMessages(roomName: String): List<Message> {
        return messageRepository.findByRoom(roomName)
    }
}