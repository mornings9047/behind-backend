package com.yourssu.behind.config.interceptor

import com.yourssu.behind.exception.user.UnAuthorizedException
import com.yourssu.behind.service.auth.JwtService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//@Configuration
@Component
class JwtInterceptor @Autowired constructor(val jwtService: JwtService) : HandlerInterceptor {
    private final val HEADER_AUTH = "Authorization"

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val token = request.getHeader(HEADER_AUTH)
        if (token != null && jwtService.isValid(token))
            return true
        else
            throw UnAuthorizedException()
    }
}
