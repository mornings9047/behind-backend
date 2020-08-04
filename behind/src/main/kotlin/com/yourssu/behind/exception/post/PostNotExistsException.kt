package com.yourssu.behind.exception.post

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "게시글이 존재하지 않습니다")
class PostNotExistsException(msg: String = "게시글이 존재하지 않습니다") : RuntimeException(msg)
