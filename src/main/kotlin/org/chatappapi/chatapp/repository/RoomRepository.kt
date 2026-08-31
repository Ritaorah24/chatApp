package org.chatappapi.chatapp.repository

import org.chatappapi.chatapp.model.Room
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository


@Repository
class RoomRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun save(room: Room) {
        jdbcTemplate.update(
            "INSERT INTO rooms(roomName) VALUES (?)",
            room.roomName
        )
    }

    fun findAll(): List<Room> {
        return jdbcTemplate.query(
            "SELECT * FROM rooms ORDER BY roomName ASC"
        ) { row, _ ->
            Room(
                roomName = row.getString("roomName")
            )
        }
    }

    fun roomExists(roomName: String): Boolean {
        val count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM rooms WHERE roomName = ?",
            Int::class.java,
            roomName
        )

        return count != null && count > 0
    }
}