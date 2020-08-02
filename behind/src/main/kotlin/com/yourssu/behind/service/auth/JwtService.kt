package com.yourssu.behind.service.auth

import com.yourssu.behind.exception.auth.TokenExpiredException
import com.yourssu.behind.exception.user.UnAuthorizedException
import com.yourssu.behind.exception.user.UserNotExistsException
import com.yourssu.behind.model.entity.user.User
import com.yourssu.behind.repository.user.UserRepository
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*

@Service
class JwtService @Autowired constructor(val userRepository: UserRepository) {
    private final val key = "A"
    private final val HEADER_AUTH = "Authorization"
    private final val ACCESS_TOKEN_EXPIRATION = 1000 * 60L * 5  // 5분
    private final val REFRESH_TOKEN_EXPIRATION = 1000 * 60L * 60 * 24 // 24시간
    private final val ACCESS_TOKEN = "ACCESS_TOKEN"
    private final val REFRESH_TOKEN = "REFRESH_TOKEN"

    fun createAccessToken(user: User): String {
        val expiration = Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION)

        val headers: MutableMap<String, Any> = HashMap()    // 헤더
        val payloads: MutableMap<String, Any> = HashMap()   // 내용

        headers["typ"] = "JWT"
        headers["alg"] = "HS256"

        payloads["schoolId"] = user.schoolId

        return Jwts.builder()
                .setSubject(ACCESS_TOKEN)
                .setHeader(headers)
                .setClaims(payloads)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key.toByteArray())
                .compact()
    }

    fun createRefreshToken(user: User): String {
        val expiration = Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION)

        val headers: MutableMap<String, Any> = HashMap()    // 헤더
        val payloads: MutableMap<String, Any> = HashMap()   // 내용

        headers["typ"] = "JWT"  // 토큰 타입
        headers["alg"] = "HS256" // 알고리즘

        payloads["schoolId"] = user.schoolId   // 데이터

        return Jwts.builder()
                .setSubject(REFRESH_TOKEN)
                .setHeader(headers)
                .setClaims(payloads)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key.toByteArray())
                // 서명(해시 알고리즘: SignatureAlgorithm.HS256, key의 byte: key.toByteArray())
                .compact()  // 토큰 생성
    }

    fun decodeToken(): Claims {
        val request = ((RequestContextHolder.currentRequestAttributes()) as ServletRequestAttributes).request
        val token = request.getHeader(HEADER_AUTH)
        return Jwts.parser().setSigningKey("A".toByteArray()).parseClaimsJws(token).body
    }

    @Throws(InterruptedException::class)
    fun isValid(token: String): Boolean {
        try {
            return !isTokenExpired(token)
        } catch (e: ExpiredJwtException) {
            throw TokenExpiredException()
        } catch (e: MalformedJwtException) {
            throw UnAuthorizedException()
        } catch (e: IllegalArgumentException) {
            throw UnAuthorizedException()
        }
    }

    fun getUser(): User {
        val schoolId = decodeToken()["schoolId"] as String
        return userRepository.findBySchoolId(schoolId).orElseThrow { UserNotExistsException() }
    }

    fun isTokenExpired(token: String): Boolean {
        val expiration = decodeToken().expiration
        return expiration.before(Date())
    }
}
