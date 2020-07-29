package com.yourssu.behind.controller.auth

import com.yourssu.behind.model.dto.user.request.UserSignInRequestDto
import com.yourssu.behind.model.dto.user.request.UserSignUpRequestDto
import com.yourssu.behind.service.auth.AuthService
import com.yourssu.behind.service.auth.JwtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(val authService: AuthService, val jwtService: JwtService) {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun signUpNewUser(@Valid @RequestBody signUpRequestDto: UserSignUpRequestDto) {
        authService.signUp(signUpRequestDto)
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    fun signIn(@Valid @RequestBody signInRequestDto: UserSignInRequestDto): String {
        val user = authService.signIn(signInRequestDto)
        return jwtService.createToken(user)
    }
}
