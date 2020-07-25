package com.yourssu.behind.exception.post

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "이미 좋아요 한 게시글 입니다.")
class AlreadyThumbsUpException : RuntimeException() {
}