package com.yourssu.behind.config.interceptor

import com.yourssu.behind.exception.auth.*
import com.yourssu.behind.exception.comment.CommentNotExistsException
import com.yourssu.behind.exception.lecture.LectureAlreadyExistsException
import com.yourssu.behind.exception.lecture.LectureNotExistsException
import com.yourssu.behind.exception.post.*
import com.yourssu.behind.exception.report.CommentAlreadyReportedException
import com.yourssu.behind.exception.report.PostAlreadyReportedException
import com.yourssu.behind.exception.user.*
import com.yourssu.behind.model.dto.exception.ExceptionResponseDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalRestExceptionHandler {
    @ExceptionHandler(value = [InvalidSchoolIdException::class, InvalidPasswordException::class, PasswordNotMatchedException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun userException(e: Exception): ExceptionResponseDto {
        return ExceptionResponseDto(HttpStatus.BAD_REQUEST, e)
    }

    @ExceptionHandler(value = [UserAlreadyExistsException::class, LectureAlreadyExistsException::class, PostAlreadyReportedException::class, CommentAlreadyReportedException::class, PostNotContainsCommentException::class])
    @ResponseStatus(HttpStatus.CONFLICT)
    fun conflictException(e: Exception): ExceptionResponseDto {
        return ExceptionResponseDto(HttpStatus.CONFLICT, e)
    }

    @ExceptionHandler(value = [InvalidFileTypeException::class, WriterScrapException::class, PostAlreadyDeletedException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun postException(e: Exception): ExceptionResponseDto {
        return ExceptionResponseDto(HttpStatus.BAD_REQUEST, e)
    }

    @ExceptionHandler(value = [InvalidTokenException::class, TokenExpiredException::class, UnAuthorizedException::class])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun authException(e: Exception): ExceptionResponseDto {
        return ExceptionResponseDto(HttpStatus.UNAUTHORIZED, e)
    }

    @ExceptionHandler(value = [TokenNotFoundException::class, UserNotExistsException::class, PostNotExistsException::class, CommentNotExistsException::class, LectureNotExistsException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundException(e: Exception): ExceptionResponseDto {
        return ExceptionResponseDto(HttpStatus.NOT_FOUND, e)
    }
}
