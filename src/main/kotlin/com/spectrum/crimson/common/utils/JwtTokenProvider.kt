package com.spectrum.crimson.common.utils

import com.spectrum.crimson.common.exception.JwtAuthException
import com.spectrum.crimson.common.exception.CrimsonException
import com.spectrum.crimson.common.service.MemberDetailsService
import com.spectrum.crimson.domain.enums.MsgKOR
import com.spectrum.crimson.domain.model.CrimsonToken
import com.spectrum.crimson.domain.model.CrimsonMemberDetail
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Base64
import java.util.Date
import java.util.UUID
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret-key}") private val secretKey: String,
    @Value("\${jwt.access.expiration-hours}") private val accessTokenExpirationHours: Long,
    @Value("\${jwt.refresh.expiration-days}") private val refreshTokenExpirationDays: Long,
    private val memberDetailsService: MemberDetailsService,
) {

    fun generateToken(auth: Authentication): CrimsonToken {
        val claims = setClaims(auth)
        val issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS)
        val accessTokenExpiration = issuedAt.plus(accessTokenExpirationHours, ChronoUnit.DAYS)
        val refreshTokenExpiration = issuedAt.plus(refreshTokenExpirationDays, ChronoUnit.DAYS)

        val accessToken = createJwtToken(
            claims = claims,
            subject = auth.name,
            issuedAt = issuedAt,
            expiration = accessTokenExpiration
        )

        val refreshToken = createJwtToken(
            claims = claims,
            subject = auth.name,
            expiration = refreshTokenExpiration
        )

        return CrimsonToken(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun reissueToken(
        auth: Authentication,
        originalRefreshToken: String
    ): CrimsonToken {
        val claims = setClaims(auth)
        val issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS)
        val accessTokenExpiration = issuedAt.plus(accessTokenExpirationHours, ChronoUnit.DAYS)

        val accessToken = createJwtToken(
            claims = claims,
            subject = auth.name,
            issuedAt = issuedAt,
            expiration = accessTokenExpiration
        )

        val originalRefreshTokenClaims = parseClaims(originalRefreshToken)
        val refreshTokenExpirationDate = originalRefreshTokenClaims.expiration.toInstant()

        val refreshToken = createJwtToken(
            claims = claims,
            subject = auth.name,
            expiration = refreshTokenExpirationDate
        )

        return CrimsonToken(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun getAuthentication(token: String): Authentication {
        val claims = parseClaims(token)
        val email = claims["email"].toString()
        val principal = memberDetailsService.loadUserByUsername(email)
        return UsernamePasswordAuthenticationToken(principal, "", ArrayList())
    }

    // 토큰 정보를 검증하는 메서드
    fun validateToken(token: String): Boolean {
        try {
            val cleanedToken = removePrefix(token.trim())
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(cleanedToken)
            return true
        } catch (e: SecurityException) {
            throw JwtAuthException(MsgKOR.INVALID_JWT_TOKEN.message)
        } catch (e: MalformedJwtException) {
            throw JwtAuthException(MsgKOR.INVALID_JWT_TOKEN.message)
        } catch (e: ExpiredJwtException) {
            throw JwtAuthException(MsgKOR.EXPIRED_JWT_TOKEN.message)
        } catch (e: UnsupportedJwtException) {
            throw JwtAuthException(MsgKOR.UNSUPPORTED_JWT_TOKEN.message)
        } catch (e: IllegalArgumentException) {
            throw JwtAuthException(MsgKOR.MISSING_JWT_CLAIMS_TOKEN.message)
        }
    }

    fun getDataFromToken(token: String, field: String): Any {
        val cleanedToken = removePrefix(token)
        if (validateToken(cleanedToken)) {
            val claims = parseClaims(cleanedToken)
            return claims[field]
                ?: throw CrimsonException(MsgKOR.INVALID_JWT_TOKEN.message)
        } else {
            throw CrimsonException(MsgKOR.INVALID_JWT_TOKEN.message)
        }
    }

    fun getRolesFromToken(token: String): Any? {
        val cleanedToken = removePrefix(token)
        if (validateToken(cleanedToken)) {
            val claims = parseClaims(cleanedToken)
            return claims["roles"]
        } else {
            throw CrimsonException(MsgKOR.INVALID_JWT_TOKEN.message)
        }
    }

    fun getUserId(token: String): String {
        return parseClaims(token)["userId"] as String
    }

    private fun createJwtToken(
        claims: Map<String, Any>,
        subject: String,
        issuedAt: Instant? = null,
        expiration: Instant
    ): String {
        val builder = Jwts.builder()
            .claims(claims)
            .subject(subject)
            .expiration(Date.from(expiration))
            .signWith(getSigningKey())

        if (issuedAt != null) {
            builder.issuedAt(Date.from(issuedAt))
        }

        return builder.compact()
    }

    fun parseClaims(token: String): Claims {
        return try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).payload
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }

    private fun getSigningKey(): SecretKey {
        val keyBytes = Base64.getDecoder().decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    private fun setClaims(auth: Authentication): Map<String, Any> {
        val claims = mutableMapOf<String, Any>()
        val id = (auth.principal as CrimsonMemberDetail).getMemberId()
        val email = (auth.principal as CrimsonMemberDetail).getEmail()
        val roles = auth.authorities.map { it.authority }

        claims["id"] = id
        claims["email"] = email
        claims["roles"] = roles
        claims["jit"] = UUID.randomUUID().toString()

        return claims
    }

    fun removePrefix(str: String): String = str.replace("Bearer ", "")
}