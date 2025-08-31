package com.spectrum.crimson.domain.redis.entity

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.annotation.Id
import java.io.Serializable
import java.time.LocalDateTime

@RedisHash(value = "user-black-access-token", timeToLive = 3600)
data class RedisBlackAccessToken(
    @Id
    val id: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
) : Serializable