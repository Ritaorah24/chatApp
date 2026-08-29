package org.chatappapi.chatapp.controller

import org.chatappapi.chatapp.model.CreateRoomDto
import org.chatappapi.chatapp.model.Room
import org.chatappapi.chatapp.service.RoomService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rooms")
class RoomController(
    private val roomService : RoomService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createRoom(@RequestBody request: CreateRoomDto): Room {
        return roomService.createRoom(request.roomName)
    }
    @GetMapping
    fun getAllRooms(): List<Room> {
        return roomService.getAllRooms()
    }
}











