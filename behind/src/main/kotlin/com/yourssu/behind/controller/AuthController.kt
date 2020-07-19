package com.yourssu.behind.controller

import com.yourssu.behind.model.dto.UserSignUpRequestDto
import com.yourssu.behind.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {
    @Autowired
    lateinit var authService: AuthService

    fun signUpNewUser(@RequestBody signUpRequestDto: UserSignUpRequestDto) {
        authService.signUp(signUpRequestDto)
    }

}
