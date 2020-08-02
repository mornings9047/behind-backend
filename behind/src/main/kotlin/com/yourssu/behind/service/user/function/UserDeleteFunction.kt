package com.yourssu.behind.service.user.function

import com.yourssu.behind.exception.user.UserNotExistException
import com.yourssu.behind.repository.user.UserRepository
import java.time.LocalDateTime

class UserDeleteFunction(private val userRepository: UserRepository) {

    fun deleteUser(userId: Long) {
        var user = userRepository.findById(userId).orElseThrow { UserNotExistException() }
        user.schoolId = "DeleteUser_${user.schoolId}_${LocalDateTime.now()}"

        userRepository.save(user)
    }
}