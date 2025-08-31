package com.spectrum.crimson.service.redis

import com.spectrum.crimson.domain.redis.entity.RedisRefreshToken
import com.spectrum.crimson.domain.redis.repository.RedisRefreshTokenRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class RedisRefreshTokenService(
    private val redisRefreshTokenRepository: RedisRefreshTokenRepository,
) {

    fun getRefreshToken(key: String): RedisRefreshToken? {
        return redisRefreshTokenRepository.findById(key).getOrNull()
    }

    fun saveRefreshToken(token: RedisRefreshToken) {
        redisRefreshTokenRepository.save(token)
    }
}