package com.spectrum.crimson.domain.redis.repository

import com.spectrum.crimson.domain.redis.entity.RedisKakaoPlace
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RedisKakaoPlaceRepository: CrudRepository<RedisKakaoPlace, String> {
    // 키워드로 검색
    fun findBySearchKeyword(keyword: String): Optional<RedisKakaoPlace>
}