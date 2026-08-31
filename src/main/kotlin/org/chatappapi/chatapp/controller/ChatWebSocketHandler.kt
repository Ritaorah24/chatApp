package org.chatappapi.chatapp.controller

import org.chatappapi.chatapp.model.Message
import org.chatappapi.chatapp.model.WebSocketNotice
import org.chatappapi.chatapp.service.MessageService
import org.chatappapi.chatapp.service.RoomService
import org.chatappapi.chatapp.service.UserService
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import tools.jackson.databind.ObjectMapper
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Component
class ChatWebSocketHandler(
    private val userService: UserService,
    private val roomService: RoomService,
    private val messageService: MessageService,
    private val objectMapper: ObjectMapper
   ) : TextWebSocketHandler() {
    private val rooms = ConcurrentHashMap<String, MutableSet<WebSocketSession>>()

    override fun afterConnectionEstablished(session: WebSocketSession) {

       val path = session.uri?.path
        val roomName = path?.split("/")?. getOrNull(2)

        val query = session.uri?.query
        val userName = query?.split("=")?.getOrNull(1)

        if (roomName == null || !roomService.roomExists(roomName)) {
            session.close()
            return
        }

        if (userName == null || !userService.userExists(userName)) {
            session.close()
            return
        }

        rooms.getOrPut(roomName) {ConcurrentHashMap.newKeySet() }.add(session)

        val notice = WebSocketNotice(
            type = "NOTICE",
            content = "$userName joined"
        )

        val jsonNotice = objectMapper.writeValueAsString(notice)

        rooms[roomName]?.forEach { userSession ->
            if (userSession.isOpen && userSession != session) {
                userSession.sendMessage(TextMessage(jsonNotice))
            }
        }

    }
    override fun handleTextMessage(
        session: WebSocketSession,
        message: TextMessage
    ) {
        val path = session.uri?.path
        val roomName = path?.split("/")?.getOrNull(2)

        val query = session.uri?.query
        val userName = query?.split("=")?.getOrNull(1)

        val newMessage = Message(
            id = 0L,
            roomName = roomName!!,
            userName = userName!!,
            content = message.payload,
            createdAt = LocalDateTime.now().toString()
        )
        val savedMessage = messageService.saveMessage(newMessage)

        val jsonMessage = objectMapper.writeValueAsString(savedMessage)

        rooms[roomName]?.forEach { userSession ->
            if (userSession.isOpen && userSession != session) {
                userSession.sendMessage(TextMessage(jsonMessage))
            }
        }
    }
    override fun afterConnectionClosed(
        session: WebSocketSession,
        status: CloseStatus
    ) {
        val path = session.uri?.path
        val roomName = path?.split("/")?.getOrNull(2)

        val query = session.uri?.query
        val userName = query?.split("=")?.getOrNull(1)

        if (roomName == null || userName == null) {
            return
        }

        rooms[roomName]?.remove(session)

        val notice = WebSocketNotice(
            type = "NOTICE",
            content = "$userName left"
        )

        val jsonNotice = objectMapper.writeValueAsString(notice)

        rooms[roomName]?.forEach { userSession ->
            if (userSession.isOpen) {
                userSession.sendMessage(TextMessage(jsonNotice))
            }
        }

        if (rooms[roomName]?.isEmpty() == true) {
            rooms.remove(roomName)
        }
    }
}