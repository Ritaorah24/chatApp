package org.chatappapi.chatapp

import org.chatappapi.chatapp.model.Message
import org.chatappapi.chatapp.service.MessageService
import org.chatappapi.chatapp.service.RoomService
import org.chatappapi.chatapp.service.UserService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.time.LocalDateTime
import java.util.UUID

@SpringBootTest
class ChatAppApplicationTests {

    @Test
    fun historyEndpointReturnsSavedMessages(
        context: WebApplicationContext
    ) {
        val mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build()

        val userService = context.getBean(UserService::class.java)
        val roomService = context.getBean(RoomService::class.java)
        val messageService = context.getBean(MessageService::class.java)

        val userName = "TestUser_${UUID.randomUUID()}"
        val roomName = "TestRoom_${UUID.randomUUID()}"

        userService.registerUser(userName)
        roomService.createRoom(roomName)

        val message = Message(
            id = 0L,
            roomName = roomName,
            userName = userName,
            content = "Hello from test",
            createdAt = LocalDateTime.now().toString()
        )

        messageService.saveMessage(message)

        val response = mockMvc.perform(
            get("/rooms/$roomName/messages")
        ).andReturn()

        assertTrue(response.response.status in 200..299)
        assertTrue(
            response.response.contentAsString.contains("Hello from test")
        )
    }
}