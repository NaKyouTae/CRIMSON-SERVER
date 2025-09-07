package com.spectrum.crimson.controller

import com.spectrum.crimson.port.KakaoSearchByKeywordPort
import com.spectrum.crimson.port.model.KakaoSearchRequest
import com.spectrum.crimson.port.model.KakaoSearchResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 카카오 로컬 API 검색 컨트롤러
 * 
 * 카카오 로컬 API를 통한 장소 검색 기능을 제공하는 REST API 엔드포인트입니다.
 */
@RestController
@RequestMapping("/api/v1/kakao/search")
class KakaoSearchController(
    private val kakaoSearchByKeywordPort: KakaoSearchByKeywordPort
) {
    
    /**
     * 키워드로 장소를 검색합니다.
     * 
     * @param query 검색할 키워드
     * @param page 페이지 번호 (기본값: 1)
     * @param size 페이지당 결과 수 (기본값: 15)
     * @return 검색 결과
     */
    @GetMapping("/keyword")
    fun searchByKeyword(
        @RequestParam query: String,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "15") size: Int
    ): ResponseEntity<KakaoSearchResponse> {
        val result = kakaoSearchByKeywordPort.searchByKeyword(query, page, size)
        return ResponseEntity.ok(result)
    }
    
    /**
     * 좌표 기반으로 키워드 검색을 수행합니다.
     * 
     * @param query 검색할 키워드
     * @param longitude 경도 (X 좌표)
     * @param latitude 위도 (Y 좌표)
     * @param radius 반경 (미터, 기본값: 20000)
     * @param sort 정렬 방식 (distance 또는 accuracy, 기본값: accuracy)
     * @return 검색 결과
     */
    @GetMapping("/keyword/location")
    fun searchByKeywordWithLocation(
        @RequestParam query: String,
        @RequestParam longitude: String,
        @RequestParam latitude: String,
        @RequestParam(defaultValue = "20000") radius: Int,
        @RequestParam(defaultValue = "accuracy") sort: String
    ): ResponseEntity<KakaoSearchResponse> {
        val result = kakaoSearchByKeywordPort.searchByKeywordWithLocation(
            query, longitude, latitude, radius, sort
        )
        return ResponseEntity.ok(result)
    }
    
    /**
     * 상세한 검색 파라미터로 장소를 검색합니다.
     * 
     * @param request 검색 요청 파라미터
     * @return 검색 결과
     */
    @PostMapping("/keyword")
    fun searchByKeywordDetailed(
        @RequestBody request: KakaoSearchRequest
    ): ResponseEntity<KakaoSearchResponse> {
        val result = kakaoSearchByKeywordPort.searchByKeyword(request)
        return ResponseEntity.ok(result)
    }
    
    /**
     * 디버깅용 엔드포인트 - "돈까스" 검색 테스트
     */
    @GetMapping("/test/donkas")
    fun testDonkasSearch(): ResponseEntity<KakaoSearchResponse> {
        val result = kakaoSearchByKeywordPort.searchByKeyword("돈까스")
        return ResponseEntity.ok(result)
    }
}
