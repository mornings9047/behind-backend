package com.yourssu.behind.controller.auth

import com.yourssu.behind.model.dto.user.request.UserSignInRequestDto
import com.yourssu.behind.model.dto.user.request.UserSignUpRequestDto
import com.yourssu.behind.service.auth.AuthService
import com.yourssu.behind.service.auth.JwtService
import com.yourssu.behind.service.auth.RedisService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(val authService: AuthService,
                                            val jwtService: JwtService,
                                            val redisService: RedisService) {
    @PostMapping("/signup")
    @ApiOperation(value = "회원가입")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUpNewUser(@Valid @RequestBody signUpRequestDto: UserSignUpRequestDto) {
        authService.signUp(signUpRequestDto)
    }

    @PostMapping("/signin")
    @ApiOperation(value = "로그인")
    @ResponseStatus(HttpStatus.OK)
    fun signIn(@Valid @RequestBody signInRequestDto: UserSignInRequestDto): String {
        val user = authService.signIn(signInRequestDto)
        return jwtService.createAccessToken(user)
    }

    @PostMapping("/signout")
    @ApiOperation(value = "로그아웃")
    @ResponseStatus(HttpStatus.OK)
    fun signOut() {
        return authService.signOut()
    }
}
