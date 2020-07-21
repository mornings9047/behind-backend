package com.yourssu.behind

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableWebMvc
class BehindApplication

fun main(args: Array<String>) {
    runApplication<BehindApplication>(*args)
}
