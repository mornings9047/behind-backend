package com.yourssu.behind.exception.post

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "이미지 형식의 파일만 저장 할 수 있습니다")
class InvalidFileTypeException(msg: String = "Invalid Image Type") : RuntimeException(msg)
