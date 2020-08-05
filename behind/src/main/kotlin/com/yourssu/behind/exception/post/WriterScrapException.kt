package com.yourssu.behind.exception.post

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "자신의 글은 스크랩 할 수 없습니다")
class WriterScrapException(msg: String = "Can't Scrap Your Own Post") : RuntimeException(msg)
