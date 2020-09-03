package com.yourssu.behind.exception

open class AppError(val errorCode: String, val msg: String) : RuntimeException(msg)