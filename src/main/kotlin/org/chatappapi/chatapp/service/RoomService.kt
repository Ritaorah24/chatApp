package org.chatappapi.chatapp.service

import org.chatappapi.chatapp.model.Room
import org.chatappapi.chatapp.exception.RoomAlreadyExistsException
import org.chatappapi.chatapp.repository.RoomRepository
import org.springframework.stereotype.Service


@Service
class RoomService(
    private val roomRepository: RoomRepository
) {
    fun createRoom(roomName: String): Room {
        if (roomName.isBlank()) {
            throw IllegalArgumentException("roomName cannot be blank")
        }
        if (roomRepository.roomExists(roomName)) {
            throw RoomAlreadyExistsException("room '$roomName' already exists")
        }
        val room = Room(roomName)

        roomRepository.save(room)

        return room
    }

    fun getAllRooms(): List<Room> {
        return roomRepository.findAll()
    }

    fun roomExists(roomName: String): Boolean {
        return roomRepository.roomExists(roomName)
    }
}
