package org.chatappapi.chatapp.model

data class User(
    val userName: String
)

data class Room(
    val roomName: String
)

data class Message(
    val roomName: String,
    val userName: String,
    val content: String,
    val createdAt: String
)

