package com.spectrum.crimson.config.filter

import com.spectrum.crimson.common.exception.JwtAuthException
import com.spectrum.crimson.service.redis.RedisBlackAccessTokenService
import com.spectrum.crimson.common.utils.JwtTokenProvider
import com.spectrum.crimson.domain.enums.MsgKOR
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JwtAuthFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val redisBlackAccessTokenService: RedisBlackAccessTokenService
) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val excludePath = arrayOf("/api/auth", "/actuator")
        val path = request.requestURI
        return excludePath.any { path.startsWith(it) }
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = resolveToken(request)

        if (token != null) {
            // 블랙 리스트 Access Token 체크
            if (redisBlackAccessTokenService.getBlackAccessToken(token) != null) {
                throw JwtAuthException(MsgKOR.INVALID_JWT_TOKEN.message)
            }

            if (jwtTokenProvider.validateToken(token)) {
                val authentication = jwtTokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = authentication
            } else {
                throw JwtAuthException(MsgKOR.INVALID_JWT_TOKEN.message)
            }
        } else {
            throw JwtAuthException(MsgKOR.MISSING_JWT_TOKEN.message)
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (!bearerToken.isNullOrBlank() && bearerToken.startsWith("Bearer")) {
            bearerToken.substring(7)
        } else {
            null
        }
    }
}