package com.spectrum.crimson.port.model

/**
 * 카카오 로컬 API 키워드 검색 요청 파라미터
 */
data class KakaoSearchRequest(
    /**
     * 검색을 원하는 질의어
     */
    val query: String,
    
    /**
     * 결과 페이지 번호 (1~45 사이의 값, 기본값: 1)
     */
    val page: Int = 1,
    
    /**
     * 한 페이지에 보여질 문서의 개수 (1~15 사이의 값, 기본값: 15)
     */
    val size: Int = 15,
    
    /**
     * 중심 좌표의 X값 혹은 경도(longitude)
     */
    val x: String? = null,
    
    /**
     * 중심 좌표의 Y값 혹은 위도(latitude)
     */
    val y: String? = null,
    
    /**
     * 중심 좌표부터의 반경거리 (단위: 미터, 0~20000 사이의 값, 기본값: 20000)
     */
    val radius: Int? = null,
    
    /**
     * 결과 정렬 순서 (distance 또는 accuracy, 기본값: accuracy)
     */
    val sort: String = "accuracy",
    
    /**
     * 카테고리 그룹 코드
     */
    val categoryGroupCode: String? = null,
    
    /**
     * 검색 결과 제공 지역 지정 (예: "서울", "경기")
     */
    val rect: String? = null
)
