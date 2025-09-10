package com.spectrum.crimson.domain.redis.entity

import com.spectrum.crimson.port.model.KakaoSearchResponse
import com.spectrum.crimson.proto.KakaoPlaceMeta
import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import java.time.LocalDateTime
import org.springframework.data.redis.core.TimeToLive

@RedisHash("kakao_place", timeToLive = 3600)
data class RedisKakaoPlace(
    @Id
    val id: String,

    val response: KakaoSearchResponse,

    val searchKeyword: String,
    val lastSearchedAt: LocalDateTime = LocalDateTime.now(),
    val searchCount: Long = 0,

    @TimeToLive
    val ttl: Long = 3600 // 1시간
)
