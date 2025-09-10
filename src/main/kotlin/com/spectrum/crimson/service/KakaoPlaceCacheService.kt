package com.spectrum.crimson.service

import com.spectrum.crimson.common.exception.CrimsonException
import com.spectrum.crimson.domain.redis.entity.RedisKakaoPlace
import com.spectrum.crimson.domain.redis.repository.RedisKakaoPlaceRepository
import com.spectrum.crimson.port.model.Document
import com.spectrum.crimson.port.model.KakaoSearchResponse
import com.spectrum.crimson.proto.place
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Service
class KakaoPlaceCacheService(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val redisKakaoPlaceRepository: RedisKakaoPlaceRepository,
) {

    private val setOperations = redisTemplate.opsForSet()

    // 검색 결과 캐싱
    suspend fun cacheSearchResults(
        id: String,
        key: String,
        query: String,
        places: KakaoSearchResponse
    ): RedisKakaoPlace {
        val redisPlaces = RedisKakaoPlace(
            id = id,
            response = places,
            searchKeyword = query,
            lastSearchedAt = LocalDateTime.now(),
            searchCount = 1,
            ttl = 3600
        )

        // 배치 저장
        val savedPlaces = redisKakaoPlaceRepository.save(redisPlaces)

        // 검색 키워드 인덱싱
        setOperations.add(key, id)
        redisTemplate.expire(key, Duration.ofHours(1))

        return savedPlaces
    }

    // 캐시에서 검색
    suspend fun searchFromCache(key: String): RedisKakaoPlace? {
        val place = redisKakaoPlaceRepository.findBySearchKeyword(key)
        return place.getOrNull()
    }

    // 키워드 추출
    private fun extractKeywords(query: String): Set<String> {
        return query.split(" ")
            .map { it.trim().lowercase() }
            .filter { it.length > 1 }
            .toSet()
    }
}