package com.spectrum.crimson.port.impl

import com.spectrum.crimson.config.KakaoApiConfig
import com.spectrum.crimson.port.KakaoSearchByKeywordPort
import com.spectrum.crimson.port.exception.KakaoApiException
import com.spectrum.crimson.port.model.KakaoSearchRequest
import com.spectrum.crimson.port.model.KakaoSearchResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

/**
 * 카카오 로컬 API 키워드 검색 포트의 구현체
 * 
 * 카카오 로컬 API를 통해 키워드로 장소를 검색하는 기능을 구현합니다.
 */
@Component
class KakaoSearchByKeywordPortImpl(
    @Qualifier("kakaoRestTemplate")
    private val restTemplate: RestTemplate,
    private val kakaoApiConfig: KakaoApiConfig
) : KakaoSearchByKeywordPort {
    
    private val logger = LoggerFactory.getLogger(KakaoSearchByKeywordPortImpl::class.java)
    
    companion object {
        private const val SEARCH_ENDPOINT = "/v2/local/search/keyword.json"
    }
    
    override fun searchByKeyword(request: KakaoSearchRequest): KakaoSearchResponse {
        logger.info("카카오 키워드 검색 시작: query={}, page={}, size={}", 
            request.query, request.page, request.size)
        
        val url = buildSearchUrl(request)
        logger.info("카카오 API 호출 URL: {}", url)
        
        return try {
            // 실제 HTTP 요청을 보내기 전에 헤더 확인
            logger.debug("RestTemplate 설정 확인 - Authorization 헤더가 설정되어 있는지 확인 필요")
            
            val response = restTemplate.getForObject(url, KakaoSearchResponse::class.java)
                ?: throw KakaoApiException("카카오 API 응답이 null입니다.")
            
            logger.info("카카오 키워드 검색 완료: 총 {}개 결과, 페이지 가능 {}개", 
                response.meta.totalCount, response.meta.pageableCount)
            
            // 응답이 비어있는 경우 추가 로깅
            if (response.documents.isEmpty()) {
                logger.warn("검색 결과가 비어있습니다. query='{}', totalCount={}, pageableCount={}", 
                    request.query, response.meta.totalCount, response.meta.pageableCount)
            }
            
            response
            
        } catch (e: HttpClientErrorException) {
            logger.error("카카오 API 클라이언트 에러: status={}, body={}", 
                e.statusCode, e.responseBodyAsString, e)
            throw KakaoApiException(
                "카카오 API 클라이언트 에러: ${e.message}",
                e.statusCode.value(),
                e
            )
        } catch (e: HttpServerErrorException) {
            logger.error("카카오 API 서버 에러: status={}, body={}", 
                e.statusCode, e.responseBodyAsString, e)
            throw KakaoApiException(
                "카카오 API 서버 에러: ${e.message}",
                e.statusCode.value(),
                e
            )
        } catch (e: RestClientException) {
            logger.error("카카오 API 호출 실패", e)
            throw KakaoApiException("카카오 API 호출 실패: ${e.message}", e)
        }
    }
    
    /**
     * 카카오 API 검색 URL을 생성합니다.
     * 
     * @param request 검색 요청 파라미터
     * @return 완성된 API URL
     */
    private fun buildSearchUrl(request: KakaoSearchRequest): String {
        val baseUrl = "${kakaoApiConfig.getBaseUrl()}$SEARCH_ENDPOINT"
        val queryParams = mutableListOf<String>()
        
        // 필수 파라미터들 추가
        queryParams.add("query=${request.query}")
        queryParams.add("page=${request.page}")
        queryParams.add("size=${request.size}")
        
        // 선택적 파라미터들 추가
        request.x?.let { 
            queryParams.add("x=$it")
            logger.debug("X 좌표 추가: {}", it)
        }
        request.y?.let { 
            queryParams.add("y=$it")
            logger.debug("Y 좌표 추가: {}", it)
        }
        request.radius?.let { 
            queryParams.add("radius=$it")
            logger.debug("반경 추가: {}", it)
        }
        request.sort.takeIf { it != "accuracy" }?.let { 
            queryParams.add("sort=$it")
            logger.debug("정렬 방식 추가: {}", it)
        }
        request.categoryGroupCode?.let { 
            queryParams.add("category_group_code=$it")
            logger.debug("카테고리 그룹 코드 추가: {}", it)
        }
        request.rect?.let { 
            queryParams.add("rect=$it")
            logger.debug("지역 제한 추가: {}", it)
        }
        
        val finalUrl = "$baseUrl?${queryParams.joinToString("&")}"
        logger.debug("최종 URL: {}", finalUrl)
        
        return finalUrl
    }
}
