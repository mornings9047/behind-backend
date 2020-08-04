package com.yourssu.behind.config.interceptor

import com.yourssu.behind.exception.auth.TokenExpiredException
import com.yourssu.behind.exception.auth.UnAuthorizedException
import com.yourssu.behind.exception.user.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalRestExceptionHandler {

    @ExceptionHandler(value = [InvalidSchoolIdException::class, InvalidPasswordException::class, PasswordNotMatchedException::class, UserAlreadyExistsException::class, UserNotExistsException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun userException(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(value = [UnAuthorizedException::class, TokenExpiredException::class])
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun authException(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.FORBIDDEN)
    }

}
