package com.yourssu.behind.exception.post

import com.yourssu.behind.exception.AppError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "게시글에 해당 댓글이 존재하지 않습니다")
class PostNotContainsCommentException(msg: String = "Post Doesn't Contain Comment") : AppError("Post-007", msg)
