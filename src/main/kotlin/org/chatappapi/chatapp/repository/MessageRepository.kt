package org.chatappapi.chatapp.repository

import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.chatappapi.chatapp.model.Message
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class MessageRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun save(message: Message): Long {
        val keyHolder: KeyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            { connection ->
                val preparedStatement = connection.prepareStatement(
                    "INSERT INTO messages(roomName, userName, content, createdAt) VALUES (?, ?, ?, ?)",
                    arrayOf("id")
                )

                preparedStatement.setString(1, message.roomName)
                preparedStatement.setString(2, message.userName)
                preparedStatement.setString(3, message.content)
                preparedStatement.setString(4, message.createdAt)

                preparedStatement
            },
            keyHolder
        )

        return keyHolder.key!!.toLong()
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