package org.chatappapi.chatapp.controller

import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class ChatWebSocketHandler : TextWebSocketHandler() {
    override fun afterConnectionEstablished(session: WebSocketSession) {
        println("A user connected")
    }
    override fun handleTextMessage(
        session: WebSocketSession,
        message: TextMessage
    ) {
        println(message.payload)
    }

    class WebSocketHandler : TextWebSocketHandler() {

    }
}