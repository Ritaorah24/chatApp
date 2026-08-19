package org.chatappapi.chatapp.repository

import org.chatappapi.chatapp.model.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository


@Repository
class UserRepository (private val jdbcTemplate: JdbcTemplate){
    fun save(user: User){
        jdbcTemplate.update("INSERT INTO users(userName)VALUES (?)", user.userName)
 
    }
    fun userExists(userName: String): Boolean {
        val count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM users WHERE username = ?",
            Int::class.java,
            userName
        )

        return count != null && count > 0
    }
}




