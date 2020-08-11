package com.yourssu.behind.exception.lecture

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "이미 추가한 강의입니다.")
class AlreadyLectureException(msg: String = "Already Lecture") : RuntimeException(msg) {
}