package com.yourssu.behind.config.interceptor

import com.yourssu.behind.exception.auth.TokenExpiredException
import com.yourssu.behind.exception.user.UnAuthorizedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalRestExceptionHandler {

    @ExceptionHandler(value = [UnAuthorizedException::class, TokenExpiredException::class])
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun unAuthorizedError(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(value = [RuntimeException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequestError(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }
}
