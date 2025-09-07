package com.spectrum.crimson.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
import java.time.Duration

/**
 * 카카오 API 설정
 * 
 * 카카오 로컬 API 호출을 위한 HTTP 클라이언트와 관련 설정을 제공합니다.
 */
@Configuration
class KakaoApiConfig(
    @Value("\${kakao.api.rest-api-key}")
    private val restApiKey: String,
    
    @Value("\${kakao.api.base-url}")
    private val baseUrl: String
) {
    
    /**
     * 카카오 API 호출을 위한 RestTemplate 빈을 생성합니다.
     * 
     * - 타임아웃 설정: 연결 5초, 읽기 10초
     * - 기본 헤더 설정: Authorization, Content-Type
     * - 에러 핸들링 설정
     */
    @Bean("kakaoRestTemplate")
    fun kakaoRestTemplate(): RestTemplate {
        val restTemplate = RestTemplateBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .readTimeout(Duration.ofSeconds(10))
            .defaultHeader(HttpHeaders.AUTHORIZATION, "KakaoAK $restApiKey")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build()
        
        // 디버깅을 위한 로깅 추가
        println("카카오 API 설정 완료:")
        println("- Base URL: $baseUrl")
        println("- API Key: ${restApiKey.take(8)}...")
        println("- Authorization Header: KakaoAK ${restApiKey.take(8)}...")
        
        return restTemplate
    }
    
    /**
     * 카카오 API 기본 URL을 반환합니다.
     */
    fun getBaseUrl(): String = baseUrl
}
