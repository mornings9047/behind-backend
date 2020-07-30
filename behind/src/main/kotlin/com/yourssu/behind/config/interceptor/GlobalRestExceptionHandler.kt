package com.yourssu.behind.config.interceptor

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.RuntimeException

@RestControllerAdvice
class GlobalRestExceptionHandler {

    @ExceptionHandler(value = [RuntimeException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequestError(e: Exception): ResponseEntity<String> {
        e.printStackTrace()
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

}
