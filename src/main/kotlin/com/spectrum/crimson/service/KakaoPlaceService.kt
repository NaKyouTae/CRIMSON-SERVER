package com.spectrum.crimson.service

import com.spectrum.crimson.port.KakaoSearchByKeywordPort
import com.spectrum.crimson.port.model.KakaoSearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class KakaoPlaceService(
    private val kakaoPlaceCacheService: KakaoPlaceCacheService,
    private val kakaoSearchByKeywordPort: KakaoSearchByKeywordPort
) {

    suspend fun searchByKeyword(query: String, page: Int, size: Int): KakaoSearchResponse {
        val cachedResult = kakaoPlaceCacheService.searchFromCache(query) // 1. 캐시에서 검색

        if (cachedResult != null) {
            return cachedResult.response
        }

        val kakaoResponse = kakaoSearchByKeywordPort.searchByKeyword(query, page, size) // 2. 카카오 API 호출

        // 3. 결과 캐싱 (비동기)
        withContext(Dispatchers.IO) {
            kakaoPlaceCacheService.cacheSearchResults("", "", query, kakaoResponse)
        }

        return kakaoResponse
    }
}