package com.yourssu.behind.exception.post

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "이미 삭제된 게시물입니다.")
class DeletedPostException(msg: String = "이미 삭제된 게시물입니다.") : RuntimeException(msg)
