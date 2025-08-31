package com.spectrum.crimson.domain.redis.repository

import com.spectrum.crimson.domain.redis.entity.RedisRefreshToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RedisRefreshTokenRepository: CrudRepository<RedisRefreshToken, String> {
}