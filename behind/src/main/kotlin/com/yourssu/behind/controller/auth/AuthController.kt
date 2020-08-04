package com.yourssu.behind.controller.auth

import com.yourssu.behind.exception.user.*
import com.yourssu.behind.model.dto.user.request.UserSignInRequestDto
import com.yourssu.behind.model.dto.user.request.UserSignUpRequestDto
import com.yourssu.behind.service.auth.AuthService
import com.yourssu.behind.service.auth.JwtService
import com.yourssu.behind.service.auth.RedisService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(val authService: AuthService,
                                            val jwtService: JwtService,
                                            val redisService: RedisService) {
    @PostMapping("/signup")
    @ApiOperation(value = "회원가입")
    fun signUpNewUser(@Valid @RequestBody signUpRequestDto: UserSignUpRequestDto): ResponseEntity<String> {
        return try {
            authService.signUp(signUpRequestDto)
            ResponseEntity("회원가입에 성공하셨습니다", HttpStatus.CREATED)
        } catch (e: InvalidSchoolIdException) {
            ResponseEntity("유효하지 않은 학번입니다", HttpStatus.BAD_REQUEST)
        } catch (e: UserAlreadyExistsException) {
            ResponseEntity("이미 존재하는 학번입니다", HttpStatus.BAD_REQUEST)
        } catch (e: InvalidPasswordException) {
            ResponseEntity("유효하지 않는 비밀번호입니다", HttpStatus.BAD_REQUEST)
        }

    }

    @PostMapping("/signin")
    @ApiOperation(value = "로그인")
    fun signIn(@Valid @RequestBody signInRequestDto: UserSignInRequestDto): ResponseEntity<String> {
        return try {
            val user = authService.signIn(signInRequestDto)
            val token = jwtService.createAccessToken(user.schoolId)
            redisService.save(user.schoolId, token)
            ResponseEntity(token, HttpStatus.OK)
        } catch (e: UserNotExistsException) {
            ResponseEntity("존재하지 않는 사용자입니다", HttpStatus.BAD_REQUEST)
        } catch (e: PasswordNotMatchedException) {
            ResponseEntity("비밀번호가 일치하지 않습니다", HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/signout")
    @ApiOperation(value = "로그아웃")
    fun signOut(): ResponseEntity<String> {
        return try {
            authService.signOut()
            ResponseEntity("로그아웃에 성공하였습니다", HttpStatus.OK)
        } catch (e: UserNotExistsException) {
            ResponseEntity("존재하지 않는 사용자입니다", HttpStatus.BAD_REQUEST)
        }
    }
}
