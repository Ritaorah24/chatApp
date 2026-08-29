package org.chatappapi.chatapp.service

import org.chatappapi.chatapp.model.User
import org.chatappapi.chatapp.exception.UserAlreadyExistsException
import org.chatappapi.chatapp.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun registerUser(userName: String): User {

        if (userName.isBlank()) {
            throw IllegalArgumentException("userName cannot be blank")
        }

        if (userRepository.userExists(userName)) {
            throw UserAlreadyExistsException(
                "user '$userName' already exists"
            )
        }

        val user = User(userName)

        userRepository.save(user)

        return user
    }
}