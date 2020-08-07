package com.yourssu.behind.exception.report

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "이미 신고한 댓글입니다.")
class AlreadyReportCommentException(msg: String = "Already Reported Comment") : RuntimeException(msg) {
}