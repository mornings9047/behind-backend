package com.yourssu.behind.exception

import java.lang.RuntimeException

class InvalidPasswordException(msg: String) : RuntimeException(msg) {
}