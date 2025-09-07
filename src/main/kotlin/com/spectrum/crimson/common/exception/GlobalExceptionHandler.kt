package com.spectrum.crimson.common.exception

import com.spectrum.crimson.port.exception.KakaoApiException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

/**
 * 전역 예외 처리기
 * 
 * 애플리케이션 전반에서 발생하는 예외를 일관되게 처리합니다.
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    
    /**
     * 카카오 API 예외 처리
     */
    @ExceptionHandler(KakaoApiException::class)
    fun handleKakaoApiException(e: KakaoApiException): ResponseEntity<ErrorResponse> {
        logger.error("카카오 API 에러 발생", e)
        
        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Kakao API Error",
            message = e.message ?: "카카오 API 호출 중 오류가 발생했습니다.",
            path = "카카오 검색 API"
        )
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
    
    /**
     * 일반적인 런타임 예외 처리
     */
    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(e: RuntimeException): ResponseEntity<ErrorResponse> {
        logger.error("런타임 예외 발생", e)
        
        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "Internal Server Error",
            message = "서버 내부 오류가 발생했습니다.",
            path = "알 수 없음"
        )
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
    
    /**
     * 잘못된 요청 파라미터 예외 처리
     */
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        logger.warn("잘못된 요청 파라미터: {}", e.message)
        
        val errorResponse = ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Bad Request",
            message = e.message ?: "잘못된 요청입니다.",
            path = "요청 파라미터"
        )
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}

/**
 * 에러 응답 모델
 */
data class ErrorResponse(
    val timestamp: LocalDateTime,
    val status: Int,
    val error: String,
    val message: String,
    val path: String
)
