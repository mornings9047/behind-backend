package com.yourssu.behind.exception.lecture

import com.yourssu.behind.exception.AppError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "이미 추가한 강의입니다.")
class LectureAlreadyExistsException(msg: String = "Lecture Already Added") : AppError("Lecture-001",msg)
