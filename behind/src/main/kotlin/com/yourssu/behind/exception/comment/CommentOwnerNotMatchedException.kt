package com.yourssu.behind.exception.comment

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "다른 사용자의 댓글을 삭제할 수 없습니다")
class CommentOwnerNotMatchedException(msg: String = "Can't Delete Other's Comment") : RuntimeException(msg)
