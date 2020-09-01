package com.yourssu.behind.exception.lecture

import com.yourssu.behind.exception.AppError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "존재하지 않는 강의입니다.")
class LectureNotExistsException(msg: String = "Lecture Not Found") : AppError("Lecture-002",msg)
