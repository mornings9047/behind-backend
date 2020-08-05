package com.yourssu.behind.exception.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "존재하지 않는 사용자입니다")
class UserNotExistsException(msg: String = "User Not Exists") : RuntimeException(msg)
