package com.yourssu.behind.exception.report

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "이미 신고한 댓글입니다.")
class CommentAlreadyReportedException(msg: String = "Comment Already Reported") : RuntimeException(msg)
