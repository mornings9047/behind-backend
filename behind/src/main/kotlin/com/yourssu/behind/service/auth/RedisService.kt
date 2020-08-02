package com.yourssu.behind.service.auth


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class RedisService @Autowired constructor(val redisTemplate: RedisTemplate<String, Any>,
                                          val jwtService: JwtService) {

    fun save(schoolId: String, refreshToken: String) {
        redisTemplate.opsForValue().set(schoolId, refreshToken)
        val expiration = jwtService.decodeToken().expiration
        setExpiration(schoolId, expiration)
    }

    fun setExpiration(schoolId: String, expiration: Date): Boolean {
//        redisTemplate.expire(schoolId, Duration(1000))
        return redisTemplate.expireAt(schoolId, expiration)
    }

    fun get(key: String): String? {
        return redisTemplate.opsForValue().get(key) as String?
    }

    fun delete(schoolId: String) {
        redisTemplate.delete(schoolId)
    }

}
