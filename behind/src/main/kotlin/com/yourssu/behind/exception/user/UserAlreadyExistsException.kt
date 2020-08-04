package com.yourssu.behind.exception.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "이미 존재하는 학번입니다")
class UserAlreadyExistsException(msg: String = "이미 존재하는 학번입니다") : RuntimeException(msg)
