package com.yourssu.behind.config.interceptor

import com.yourssu.behind.exception.auth.InvalidTokenException
import com.yourssu.behind.exception.auth.TokenExpiredException
import com.yourssu.behind.exception.auth.TokenNotFoundException
import com.yourssu.behind.exception.auth.UnAuthorizedException
import com.yourssu.behind.exception.comment.CommentNotExistsException
import com.yourssu.behind.exception.lecture.LectureNotExistsException
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

    @ExceptionHandler(value = [InvalidSchoolIdException::class, InvalidPasswordException::class, PasswordNotMatchedException::class, UserAlreadyExistsException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun userException(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [InvalidFileTypeException::class, WriterScrapException::class, DeletedPostException::class])
    fun postException(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [InvalidTokenException::class, TokenExpiredException::class, UnAuthorizedException::class])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun authException(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(value = [UserNotExistsException::class, PostNotExistsException::class, CommentNotExistsException::class, LectureNotExistsException::class, TokenNotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundException(e: Exception): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.NOT_FOUND)
    }

}
