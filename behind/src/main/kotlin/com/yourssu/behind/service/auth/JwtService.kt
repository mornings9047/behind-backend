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
    private final val ACCESS_TOKEN_EXPIRATION = 1000 * 60L * 2
    private final val ACCESS_TOKEN = "ACCESS_TOKEN"

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

    fun getToken(): String {
        val request = ((RequestContextHolder.currentRequestAttributes()) as ServletRequestAttributes).request
        return request.getHeader(HEADER_AUTH)
    }

    fun decodeToken(): Claims {
        return Jwts.parser().setSigningKey("A".toByteArray()).parseClaimsJws(getToken()).body
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
