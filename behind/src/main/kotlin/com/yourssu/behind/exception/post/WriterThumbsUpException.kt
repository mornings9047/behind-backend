package com.yourssu.behind.exception.post

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "자신의 글에 좋아요를 누를 수 없습니다.")
class WriterThumbsUpException : RuntimeException() {
}