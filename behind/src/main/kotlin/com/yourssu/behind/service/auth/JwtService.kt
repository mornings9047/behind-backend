package com.yourssu.behind.service.auth

import com.yourssu.behind.exception.user.UnAuthorizedException
import com.yourssu.behind.model.entity.user.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
class JwtService {
    private final val key = "A"

    fun createToken(user: User): String {
        val expiredTime = 100 * 60L * 2 // 만료기간 2분
        val now = Date().time + expiredTime

        val headers: MutableMap<String, Any> = HashMap()    // 헤더
        val payloads: MutableMap<String, Any> = HashMap()   // 내용

        headers["typ"] = "JWT"  // 토큰 타입
        headers["alg"] = "HS256" // 알고리즘

        payloads["exp"] = now   // 만료시간
        payloads["data"] = user   // 데이터

        return Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .signWith(SignatureAlgorithm.HS256, key.toByteArray())
                // 서명(해시 알고리즘: SignatureAlgorithm.HS256, key의 byte: key.toByteArray())
                .compact()  // 토큰 생성
    }

    @Throws(InterruptedException::class)
    fun isValid(token: String): Boolean {
        try {
            return Jwts.parser()
                    .setSigningKey("A".toByteArray())
                    .parseClaimsJws(token)
                    .body != null
        } catch (e: Exception) {
            throw UnAuthorizedException()
        }
    }

    fun getData(key: String): Map<String, Any> {
        val request: HttpServletRequest = ((RequestContextHolder.currentRequestAttributes()) as ServletRequestAttributes).request
        val token = request.getHeader("Authorization")

        try {
            return Jwts.parser().setSigningKey("A".toByteArray()).parseClaimsJws(token).body as LinkedHashMap<String, Any>
        } catch (e: Exception) {
            throw UnAuthorizedException()
        }
    }

}
