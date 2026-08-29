package org.chatappapi.chatapp.repository

import org.chatappapi.chatapp.model.Message
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class MessageRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun save(message: Message) {
        jdbcTemplate.update(
            "INSERT INTO messages( roomName, userName, content, createdAt) VALUES (?, ?, ?, ?)",
            message.roomName,
            message.userName,
            message.content,
            message.createdAt
        )
    }
    fun findByRoom(roomName: String): List<Message> =
        jdbcTemplate.query(
            "SELECT * FROM messages WHERE roomName = ? ORDER BY createdAt ASC",
            { row, _ ->
                Message(
                    id = row.getLong("id"),
                    roomName = row.getString("roomName"),
                    userName = row.getString("userName"),
                    content = row.getString("content"),
                    createdAt = row.getString("createdAt")
                )
            },
            roomName
        )
}