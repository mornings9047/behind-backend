package com.yourssu.behind.exception.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "이미 존재하는 학번입니다.")
class UserAlreadyExistsException : RuntimeException()
