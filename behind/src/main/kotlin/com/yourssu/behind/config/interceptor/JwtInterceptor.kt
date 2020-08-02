package com.yourssu.behind.config.interceptor

import com.yourssu.behind.service.auth.JwtService
import com.yourssu.behind.service.auth.RedisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtInterceptor @Autowired constructor(val jwtService: JwtService,
                                            val redisService: RedisService) : HandlerInterceptor {
    private final val HEADER_AUTH = "Authorization"

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val token = request.getHeader(HEADER_AUTH)
        return if (token != null && jwtService.isValid(token)) {
            val user = jwtService.getUser()
            return redisService.get(user.schoolId).isNullOrBlank()
        } else false
    }
}
