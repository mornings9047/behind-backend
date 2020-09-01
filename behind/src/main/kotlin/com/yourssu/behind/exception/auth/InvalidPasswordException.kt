package com.yourssu.behind.exception.auth

import com.yourssu.behind.exception.AppError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "유효하지 않는 비밀번호입니다")
class InvalidPasswordException(msg: String = "Invalid Password") : AppError("Auth-005",msg)
