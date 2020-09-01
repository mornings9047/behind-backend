package com.yourssu.behind.exception.auth

import com.yourssu.behind.exception.AppError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "유효하지 않은 학번입니다")
class InvalidSchoolIdException(msg: String = "Invalid SchoolId") : AppError("Auth-006",msg)
