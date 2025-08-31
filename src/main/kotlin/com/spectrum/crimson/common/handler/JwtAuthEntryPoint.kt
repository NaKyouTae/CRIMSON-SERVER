package com.spectrum.crimson.common.handler

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.spectrum.crimson.common.utils.TimeUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val res = mutableMapOf<String, String>()

        res.put("timestamp", TimeUtil.nowToString())
        res.put("success", "false")
        res.put("status", HttpServletResponse.SC_UNAUTHORIZED.toString())
        res.put("message", authException.message.toString())

        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE

        val objectMapper = jacksonObjectMapper()
        val jsonResponse = objectMapper.writeValueAsString(res)

        response.writer.println(jsonResponse)
    }
}