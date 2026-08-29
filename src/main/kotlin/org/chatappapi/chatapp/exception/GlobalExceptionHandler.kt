package org.chatappapi.chatapp.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleUserAlreadyExists(exception: UserAlreadyExistsException) : String {
        return exception.message ?: "User already exists"
    }
    @ExceptionHandler(RoomAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleRoomAlreadyExists(exception: RoomAlreadyExistsException) : String {
          return exception.message ?: "Room already exists"
    }
    @ExceptionHandler (IllegalArgumentException::class)
    @ResponseStatus ( HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(exception: IllegalArgumentException) : String {
        return exception.message ?: "Invalid request"
    }
}