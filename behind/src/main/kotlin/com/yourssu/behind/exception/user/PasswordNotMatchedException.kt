package com.yourssu.behind.exception.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "비밀번호가 일치하지 않습니다")
class PasswordNotMatchedException : RuntimeException()
