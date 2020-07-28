package com.yourssu.behind.controller.auth

import com.yourssu.behind.model.dto.user.request.UserSignInRequestDto
import com.yourssu.behind.model.dto.user.request.UserSignUpRequestDto
import com.yourssu.behind.service.auth.AuthService
import com.yourssu.behind.service.auth.JwtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(val authService: AuthService, val jstService: JwtService) {

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun signUpNewUser(@Valid @RequestBody signUpRequestDto: UserSignUpRequestDto): ResponseEntity<String> {
        return try {
            authService.signUp(signUpRequestDto)
            ResponseEntity(HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/signin")
    fun signIn(@Valid @RequestBody signInRequestDto: UserSignInRequestDto): ResponseEntity<String> {
        return try {
            val user = authService.signIn(signInRequestDto)
            val token = jstService.createToken(user)
            ResponseEntity(token, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
        }
    }
}
