package com.yourssu.behind.service.auth


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisService @Autowired constructor(val redisTemplate: RedisTemplate<String, Any>,
                                          val jwtService: JwtService) {
    fun save(schoolId: String, refreshToken: String) {
        redisTemplate.opsForValue().set(schoolId, refreshToken)
//        redisTemplate.opsForValue().set(schoolId, refreshToken, jwtService.ACCESS_TOKEN_EXPIRATION, TimeUnit.MILLISECONDS)
    }

    fun get(key: String): String? {
        return redisTemplate.opsForValue().get(key) as String?
    }

    fun delete(schoolId: String) {
        redisTemplate.delete(schoolId)
    }
}
