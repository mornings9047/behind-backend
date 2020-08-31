package com.yourssu.behind.model.dto.exception

import org.springframework.http.HttpStatus
import java.util.*

class ExceptionResponseDto(val status: HttpStatus, exception: Exception) {
    val error: Int = status.value()
    val message: String? = exception.message
    val timestamp: Date = Date(System.currentTimeMillis())
}
