package com.yourssu.behind.exception.report

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "이미 신고한 게시물 입니다.")
class AlreadyReportPostException(msg: String = "Already Reported Post") : RuntimeException(msg) {
}