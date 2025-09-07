package com.spectrum.crimson.port

import com.spectrum.crimson.port.exception.KakaoApiException
import com.spectrum.crimson.port.model.KakaoSearchRequest
import com.spectrum.crimson.port.model.KakaoSearchResponse

/**
 * 카카오 로컬 API 키워드 검색을 위한 포트 인터페이스
 * 
 * 이 인터페이스는 카카오 로컬 API를 통해 키워드로 장소를 검색하는 기능을 제공합니다.
 * 헥사고날 아키텍처의 포트 패턴을 따르며, 외부 API와의 의존성을 역전시킵니다.
 */
interface KakaoSearchByKeywordPort {
    
    /**
     * 키워드로 장소를 검색합니다.
     * 
     * @param request 검색 요청 파라미터
     * @return 검색 결과 응답
     * @throws KakaoApiException 카카오 API 호출 실패 시
     */
    fun searchByKeyword(request: KakaoSearchRequest): KakaoSearchResponse
    
    /**
     * 간단한 키워드로 장소를 검색합니다.
     * 
     * @param query 검색할 키워드
     * @param page 페이지 번호 (기본값: 1)
     * @param size 페이지당 결과 수 (기본값: 15)
     * @return 검색 결과 응답
     */
    fun searchByKeyword(
        query: String, 
        page: Int = 1, 
        size: Int = 15
    ): KakaoSearchResponse {
        return searchByKeyword(
            KakaoSearchRequest(
                query = query,
                page = page,
                size = size
            )
        )
    }
    
    /**
     * 좌표 기반으로 키워드 검색을 수행합니다.
     * 
     * @param query 검색할 키워드
     * @param longitude 경도 (X 좌표)
     * @param latitude 위도 (Y 좌표)
     * @param radius 반경 (미터, 기본값: 20000)
     * @param sort 정렬 방식 (distance 또는 accuracy, 기본값: accuracy)
     * @return 검색 결과 응답
     */
    fun searchByKeywordWithLocation(
        query: String,
        longitude: String,
        latitude: String,
        radius: Int = 20000,
        sort: String = "accuracy"
    ): KakaoSearchResponse {
        return searchByKeyword(
            KakaoSearchRequest(
                query = query,
                x = longitude,
                y = latitude,
                radius = radius,
                sort = sort
            )
        )
    }
}