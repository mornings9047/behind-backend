package com.yourssu.behind.config.interceptor

import com.yourssu.behind.exception.auth.TokenExpiredException
import com.yourssu.behind.exception.auth.UnAuthorizedException
import com.yourssu.behind.exception.lecture.LectureNotExistException
import com.yourssu.behind.exception.post.DeletedPostException
import com.yourssu.behind.exception.post.InvalidFileTypeException
import com.yourssu.behind.exception.post.PostNotExistsException
import com.yourssu.behind.exception.post.WriterScrapException
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

    @ExceptionHandler(value = [LectureNotExistException::class, InvalidFileTypeException::class, PostNotExistsException::class, WriterScrapException::class, DeletedPostException::class])
    fun postException(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [UnAuthorizedException::class, TokenExpiredException::class])
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun authException(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.FORBIDDEN)
    }

}
