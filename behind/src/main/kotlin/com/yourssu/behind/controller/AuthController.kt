package com.yourssu.behind.controller

import com.yourssu.behind.exception.InvalidPasswordException
import com.yourssu.behind.exception.InvalidSchoolIdException
import com.yourssu.behind.exception.InvalidUsernameException
import com.yourssu.behind.exception.UserAlreadyExistsException
import com.yourssu.behind.model.dto.UserSignUpRequestDto
import com.yourssu.behind.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
class AuthController {
    @Autowired
    lateinit var authService: AuthService

    fun signUpNewUser(@RequestBody signUpRequestDto: UserSignUpRequestDto) {
        try {
            return authService.signUp(signUpRequestDto)
        } catch (e: InvalidSchoolIdException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 학번입니다", e)
        } catch (e: InvalidUsernameException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 아이디입니다", e)
        } catch (e: InvalidPasswordException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "유효하지 않은 비밀번호입니다", e)
        } catch (e: UserAlreadyExistsException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 학번입니다", e)
        }
    }

}
