package com.yourssu.behind.exception.auth

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "계정 권한이 유효하지 않습니다")
class UnAuthorizedException(msg: String = "UnAuthorized User") : RuntimeException(msg)
