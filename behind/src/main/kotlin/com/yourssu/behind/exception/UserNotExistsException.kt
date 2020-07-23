package com.yourssu.behind.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "존재하지 않는 학번입니다.")
class UserNotExistsException : RuntimeException()
