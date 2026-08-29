package org.chatappapi.chatapp.exception

class UserAlreadyExistsException(message: String) : RuntimeException(message)
class RoomAlreadyExistsException(message: String) : RuntimeException(message)
class RoomNotFoundException(message: String) : RuntimeException(message)
class UserNotFoundException(message: String) : RuntimeException(message)
class InvalidMessageException(message: String) : RuntimeException(message)
