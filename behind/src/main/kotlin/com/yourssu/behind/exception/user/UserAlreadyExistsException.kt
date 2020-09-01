package com.yourssu.behind.exception.user

import com.yourssu.behind.exception.AppError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "이미 존재하는 학번입니다")
class UserAlreadyExistsException(msg: String = "User Already Exists") : AppError("User-001",msg)
