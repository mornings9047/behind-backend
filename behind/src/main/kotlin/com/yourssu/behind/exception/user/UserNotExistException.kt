package com.yourssu.behind.exception.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "존재하지 않는 사용자 입니다.")
class UserNotExistException : RuntimeException()
