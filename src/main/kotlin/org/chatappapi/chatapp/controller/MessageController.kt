package org.chatappapi.chatapp.controller

import org.chatappapi.chatapp.model.Message
import org.chatappapi.chatapp.service.MessageService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rooms")

class MessageController (
    private val  messageService: MessageService
){
    @GetMapping("/{roomName}/messages")
    fun getMessages(@PathVariable roomName : String) : List<Message> {
    return messageService.getMessages(roomName)
    }

}