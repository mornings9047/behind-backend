package com.yourssu.behind.exception.comment

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "댓글을 찾을 수 없습니다")
class CommentNotExistsException(msg: String = "Comment Not Found") : RuntimeException(msg)
