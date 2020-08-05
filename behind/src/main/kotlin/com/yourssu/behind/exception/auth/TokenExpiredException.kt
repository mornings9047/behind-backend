package com.yourssu.behind.exception.auth

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "토큰 기한이 만료되었습니다")
class TokenExpiredException(msg: String = "Token expired") : RuntimeException(msg)
