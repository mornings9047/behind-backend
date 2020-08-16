package com.yourssu.behind.controller.auth

import com.yourssu.behind.model.dto.user.request.UserSignInRequestDto
import com.yourssu.behind.model.dto.user.request.UserSignUpRequestDto
import com.yourssu.behind.service.auth.AuthService
import com.yourssu.behind.service.auth.JwtService
import com.yourssu.behind.service.auth.MailService
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
                                            val redisService: RedisService,
                                            val mailService: MailService) {
    @PostMapping("/signUp")
    @ApiOperation(value = "회원가입")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUpNewUser(@Valid @RequestBody signUpRequestDto: UserSignUpRequestDto) {
        return authService.signUp(signUpRequestDto)
    }

    @PostMapping("/signIn")
    @ApiOperation(value = "로그인")
    @ResponseStatus(HttpStatus.OK)
    fun signIn(@Valid @RequestBody signInRequestDto: UserSignInRequestDto): String {
        val user = authService.signIn(signInRequestDto)
        val token = jwtService.createAccessToken(user.schoolId)
        redisService.save(user.schoolId, token)
        return token
    }

    @PostMapping("/signOut")
    @ApiOperation(value = "로그아웃")
    @ResponseStatus(HttpStatus.OK)
    fun signOut() {
        return authService.signOut()
    }

    @GetMapping("/mail")
    @ApiOperation("인증메일 전송")
    @ResponseStatus(HttpStatus.OK)
    fun sendMail(@RequestParam userEmail: String) {
        mailService.sendMail()
    }
}
