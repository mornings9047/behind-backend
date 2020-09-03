package com.yourssu.behind.exception.post

import com.yourssu.behind.exception.AppError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "다른 사용자의 게시물을 삭제할 수 없습니다")
class PostOwnerNotMatchedException(msg: String = "Can't Delete Other's Post") : AppError("Post-004",msg)
