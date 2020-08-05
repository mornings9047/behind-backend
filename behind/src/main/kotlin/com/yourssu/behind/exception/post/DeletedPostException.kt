package com.yourssu.behind.exception.post

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "이미 삭제된 게시물입니다")
class DeletedPostException(msg: String = "Post Already Deleted") : RuntimeException(msg)
