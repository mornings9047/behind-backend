package com.yourssu.behind.model.dto.exception

import com.yourssu.behind.exception.AppError
import org.springframework.http.HttpStatus
import java.util.*

class ExceptionResponseDto(private val status: HttpStatus, exception : Exception) {
    val error: Int = status.value()
    val message: String? = exception.message
    val timestamp: Date = Date(System.currentTimeMillis())
    val errorCode : String = (exception as AppError).errorCode
}
