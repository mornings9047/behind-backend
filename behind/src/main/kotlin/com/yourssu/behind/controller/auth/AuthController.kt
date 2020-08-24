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
    @PostMapping("/signUp")
    @ApiOperation(value = "회원가입")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUpNewUser(@Valid @RequestBody signUpRequestDto: UserSignUpRequestDto) {
        return authService.signUp(signUpRequestDto)
    }

    @PostMapping("/check/{schoolId}")
    @ApiOperation(value = "아이디 유효검사")
    @ResponseStatus(HttpStatus.OK)
    fun checkSchoolId(@PathVariable schoolId: String): Boolean {
        return authService.checkSchoolId(schoolId)
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
}
