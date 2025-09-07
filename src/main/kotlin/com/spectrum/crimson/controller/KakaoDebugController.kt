package com.spectrum.crimson.controller

import com.spectrum.crimson.config.KakaoApiConfig
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

/**
 * 카카오 API 디버깅용 컨트롤러
 * 
 * API 키와 설정이 제대로 되어있는지 확인하는 엔드포인트들
 */
@RestController
@RequestMapping("/api/v1/kakao/debug")
class KakaoDebugController(
    @Qualifier("kakaoRestTemplate")
    private val restTemplate: RestTemplate,
    private val kakaoApiConfig: KakaoApiConfig
) {
    
    /**
     * 카카오 API 설정 정보 확인
     */
    @GetMapping("/config")
    fun getConfig(): ResponseEntity<Map<String, String>> {
        val config = mapOf(
            "baseUrl" to kakaoApiConfig.getBaseUrl(),
            "restTemplateClass" to restTemplate.javaClass.simpleName
        )
        return ResponseEntity.ok(config)
    }
    
    /**
     * 직접 HTTP 요청으로 카카오 API 테스트
     */
    @GetMapping("/direct-test")
    fun directApiTest(): ResponseEntity<Any> {
        return try {
            val url = "${kakaoApiConfig.getBaseUrl()}/v2/local/search/keyword.json?query=돈까스&page=1&size=15"
            
            // RestTemplate의 헤더 확인
            val headers = restTemplate.headForHeaders(url)
            
            val response = restTemplate.getForObject(url, Map::class.java)
            
            ResponseEntity.ok(mapOf(
                "url" to url,
                "headers" to headers.toSingleValueMap(),
                "response" to response
            ))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf(
                "error" to e.message,
                "type" to e.javaClass.simpleName
            ))
        }
    }
    
    /**
     * 간단한 검색 테스트
     */
    @GetMapping("/simple-test")
    fun simpleTest(): ResponseEntity<Any> {
        return try {
            val url = "${kakaoApiConfig.getBaseUrl()}/v2/local/search/keyword.json?query=스타벅스&page=1&size=5"
            val response = restTemplate.getForObject(url, Map::class.java)
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf(
                "error" to e.message,
                "type" to e.javaClass.simpleName
            ))
        }
    }
}
