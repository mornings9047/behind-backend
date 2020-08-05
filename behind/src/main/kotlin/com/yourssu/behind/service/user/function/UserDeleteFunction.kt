package com.yourssu.behind.service.user.function

import com.yourssu.behind.repository.user.UserRepository
import com.yourssu.behind.service.auth.JwtService
import java.time.LocalDateTime

class UserDeleteFunction(private val userRepository: UserRepository,
                         private val jwtService: JwtService) {

    fun deleteUser() {
        val user = jwtService.getUser()
        user.schoolId = "DeleteUser_${user.schoolId}_${LocalDateTime.now()}"
        userRepository.save(user)
    }
}
