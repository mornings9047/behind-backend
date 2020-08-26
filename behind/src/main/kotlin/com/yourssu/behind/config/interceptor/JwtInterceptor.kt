package com.yourssu.behind.config.interceptor

import com.yourssu.behind.exception.auth.UnAuthorizedException
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
    @Override
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val user = jwtService.getUser()
        if (redisService.get(user.schoolId).isNullOrBlank())
            throw UnAuthorizedException()
        return true
    }
}
