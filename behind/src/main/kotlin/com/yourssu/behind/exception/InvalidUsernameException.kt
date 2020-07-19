package com.yourssu.behind.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "유효하지 않는 아이디입니다")
class InvalidUsernameException : RuntimeException() {
}
