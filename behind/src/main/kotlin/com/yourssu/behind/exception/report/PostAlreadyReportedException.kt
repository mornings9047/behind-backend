package com.yourssu.behind.exception.report

import com.yourssu.behind.exception.AppError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "이미 신고한 게시물 입니다.")
class PostAlreadyReportedException(msg: String = "Post Already Reported") : AppError("Post-006",msg)
