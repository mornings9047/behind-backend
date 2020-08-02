package com.yourssu.behind.service.auth


import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class RedisService @Autowired constructor(val jwtService: JwtService,
                                          val redisTemplate: RedisTemplate<String, Any>) {

    fun save(schoolId: String, refreshToken: String) {
        redisTemplate.opsForValue().set(schoolId, refreshToken)
        val expiration = Jwts.parser().setSigningKey("A".toByteArray()).parseClaimsJws(refreshToken).body.expiration
        setExpiration(schoolId, expiration)
    }

    fun get(key: String): String? {
        return redisTemplate.opsForValue().get(key) as String?
    }

    fun delete(schoolId: String): Boolean {
        return redisTemplate.delete(schoolId)
    }

    fun setExpiration(schoolId: String, expiration: Date): Boolean {
        return redisTemplate.expireAt(schoolId, expiration)
    }

}
