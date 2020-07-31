package com.yourssu.behind.service.auth

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
import javax.servlet.http.HttpServletRequest

@Service
class JwtService @Autowired constructor(val userRepository: UserRepository) {
    private final val key = "A"

    fun createToken(user: User): String {
        val expiredTime = 100 * 60L * 2 // 만료기간 2분
        val expiration = Date(System.currentTimeMillis() + expiredTime)

        val headers: MutableMap<String, Any> = HashMap()    // 헤더
        val payloads: MutableMap<String, Any> = HashMap()   // 내용

        headers["typ"] = "JWT"  // 토큰 타입
        headers["alg"] = "HS256" // 알고리즘

        payloads["exp"] = expiration   // 만료시간
        payloads["schoolId"] = user.schoolId   // 데이터

        return Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key.toByteArray())
                // 서명(해시 알고리즘: SignatureAlgorithm.HS256, key의 byte: key.toByteArray())
                .compact()  // 토큰 생성
    }

    @Throws(InterruptedException::class)
    fun isValid(token: String): Boolean {
        try {
            val schoolId = Jwts.parser().setSigningKey("A".toByteArray()).parseClaimsJws(token).body["schoolId"] as String
            return userRepository.findBySchoolId(schoolId).isPresent
        } catch (e: ExpiredJwtException) {
            e.printStackTrace()
            println("ExpiredJwtException")
            throw UnAuthorizedException()
        } catch (e: UnsupportedJwtException) {
            e.printStackTrace()
            println("UnsupportedJwtException")
            throw UnAuthorizedException()
        } catch (e: MalformedJwtException) {
            e.printStackTrace()
            println("MalformedJwtException")
            throw UnAuthorizedException()
        } catch (e: SignatureException) {
            e.printStackTrace()
            println("SignatureException")
            throw UnAuthorizedException()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            println("IllegalArgumentException")
            throw UnAuthorizedException()
        }
    }

    fun getUser(): User {
        val request = ((RequestContextHolder.currentRequestAttributes()) as ServletRequestAttributes).request
        val token = request.getHeader("Authorization")
        try {
            val schoolId = Jwts.parser().setSigningKey("A".toByteArray()).parseClaimsJws(token).body["schoolId"] as String
            return userRepository.findBySchoolId(schoolId).orElseThrow { UserNotExistsException() }
        } catch (e: Exception) {
            throw UnAuthorizedException()
        }
    }
}
