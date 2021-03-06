package com.yourssu.behind.exception.auth

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "토큰이 존재하지 않습니다")
class TokenNotFoundException(msg: String = "Token Not Exists") : RuntimeException(msg)
