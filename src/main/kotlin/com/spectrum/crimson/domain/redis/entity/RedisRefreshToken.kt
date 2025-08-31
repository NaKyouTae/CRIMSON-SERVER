package com.spectrum.crimson.domain.redis.entity

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.TimeToLive
import java.io.Serializable

@RedisHash("user-refresh-token")
data class RedisRefreshToken(
    @Id
    val id: String,
    @TimeToLive
    val ttl: Int? = null,
    val value: String,
) : Serializable