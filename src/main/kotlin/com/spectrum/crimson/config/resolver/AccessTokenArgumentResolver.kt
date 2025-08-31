package com.spectrum.crimson.config.resolver

import com.spectrum.crimson.common.utils.JwtTokenProvider
import com.spectrum.crimson.config.resolver.model.AccessToken
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AccessTokenArgumentResolver(
    private val jwtTokenProvider: JwtTokenProvider
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(AccessToken::class.java) != null
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val request = webRequest.nativeRequest as HttpServletRequest
        val token = request.getHeader("Authorization")?.removePrefix("Bearer ")?.trim()
        if (token.isNullOrBlank()) return null

        // JWT 파싱해서 userId, type 추출
        val claims = jwtTokenProvider.parseClaims(token)
        val id = claims["id"] as? String ?: ""
        return id
    }
}