package com.yourssu.behind.exception.post

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "이미지를 업로드 할 수 없습니다")
class ImgUploadException(msg: String = "Can't Upload Image") : RuntimeException(msg)
