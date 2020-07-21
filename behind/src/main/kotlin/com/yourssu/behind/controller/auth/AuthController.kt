package com.yourssu.behind.controller.auth

import com.yourssu.behind.model.dto.user.request.UserSignUpRequestDto
import com.yourssu.behind.service.auth.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(val authService: AuthService) {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun signUpNewUser(@Valid @RequestBody signUpRequestDto: UserSignUpRequestDto) {
        return authService.signUp(signUpRequestDto)
    }

}
