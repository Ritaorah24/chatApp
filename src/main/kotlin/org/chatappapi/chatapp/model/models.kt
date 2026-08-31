package org.chatappapi.chatapp.model

data class User(
    val userName: String
)

data class Room(
    val roomName: String
)

data class Message(
    val id: Long,
    val roomName: String,
    val userName: String,
    val content: String,
    val createdAt: String
)

data class WebSocketNotice(
    val type: String,
    val content: String
)

