package com.yourssu.behind.exception.post

import com.yourssu.behind.exception.AppError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "이미 삭제된 게시물입니다")
class PostAlreadyDeletedException(msg: String = "Post Already Deleted") : AppError("Post-001",msg)
