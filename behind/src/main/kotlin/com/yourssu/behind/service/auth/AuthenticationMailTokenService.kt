package com.yourssu.behind.service.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.util.*


@Service
class AuthenticationMailTokenService {
    private final val key = "A"
    private final val HEADER_AUTH = "Authorization"
    val ACCESS_TOKEN_EXPIRATION = 1000 * 60L * 5   // 5분
    private final val ACCESS_TOKEN = "ACCESS_TOKEN"

    fun createAuthenticationToken(schoolId: String): String {
        val expiration = Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION)
        val headers: MutableMap<String, Any> = HashMap()    // 헤더
        val payloads: MutableMap<String, Any> = HashMap()   // 내용
        headers["typ"] = "JWT"
        headers["alg"] = "HS256"
        payloads["schoolId"] = schoolId
        return Jwts.builder()
                .setSubject(ACCESS_TOKEN)
                .setHeader(headers)
                .setClaims(payloads)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, key.toByteArray())
                .compact()
    }

}