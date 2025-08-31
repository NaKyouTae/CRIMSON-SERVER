package com.spectrum.crimson.domain.redis.repository

import com.spectrum.crimson.domain.redis.entity.RedisBlackAccessToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RedisBlackAccessTokenRepository: CrudRepository<RedisBlackAccessToken, String> {
}