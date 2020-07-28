package com.yourssu.behind

import com.yourssu.behind.config.interceptor.JwtInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class BehindApplication @Autowired constructor(val jwtInterceptor: JwtInterceptor) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")   // 기본 적용 경로
                .excludePathPatterns("/auth")  // 적용 예외 경로
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
    }
}

fun main(args: Array<String>) {
    runApplication<BehindApplication>(*args)
}
