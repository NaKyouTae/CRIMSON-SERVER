package com.spectrum.crimson.common.service

import com.spectrum.crimson.domain.redis.entity.RedisBlackAccessToken
import com.spectrum.crimson.domain.redis.repository.RedisBlackAccessTokenRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class RedisBlackAccessTokenService(
    private val redisBlackAccessTokenRepository: RedisBlackAccessTokenRepository,
) {
    fun getBlackAccessToken(key: String): RedisBlackAccessToken? {
        return redisBlackAccessTokenRepository.findById(key).getOrNull()

    }

    fun saveBlackAccessToken(token: RedisBlackAccessToken) {
        redisBlackAccessTokenRepository.save(token)
    }
}