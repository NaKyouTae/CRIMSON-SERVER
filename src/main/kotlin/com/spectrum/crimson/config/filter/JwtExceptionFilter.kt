package com.spectrum.crimson.config.filter

import com.spectrum.crimson.common.utils.TimeUtil

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.spectrum.crimson.common.exception.JwtAuthException
import com.spectrum.crimson.common.model.JwtErrorResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtExceptionFilter : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (ex: JwtAuthException) {
            setResponse(request.requestURI, response, HttpStatus.UNAUTHORIZED, ex)
        } catch (ex: IOException) {
            setResponse(request.requestURI, response, HttpStatus.INTERNAL_SERVER_ERROR, ex)
        }
    }

    @Throws(RuntimeException::class, IOException::class)
    private fun setResponse(
        path: String,
        response: HttpServletResponse,
        status: HttpStatus,
        ex: Throwable
    ) {
        val errorResponse = JwtErrorResponse(
            timestamp = TimeUtil.nowToString(),
            status = status.name,
            code = status.value(),
            error = ex.message ?: "Unknown error",
            path = path,
        )
        val objectMapper = jacksonObjectMapper()
        val jsonResponse = objectMapper.writeValueAsString(errorResponse)

        response.status = status.value()
        response.contentType = "application/json;charset=UTF-8"
        response.writer.print(jsonResponse)
    }
}