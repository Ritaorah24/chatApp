package org.chatappapi.chatapp.controller

import org.chatappapi.chatapp.model.RegisterUserDto
import org.chatappapi.chatapp.model.User
import org.chatappapi.chatapp.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(@RequestBody request: RegisterUserDto): User {
        return userService.registerUser(request.userName)
    }
}