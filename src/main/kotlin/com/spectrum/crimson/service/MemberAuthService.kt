package com.spectrum.crimson.service

import com.spectrum.crimson.common.exception.CrimsonException
import com.spectrum.crimson.service.redis.RedisBlackAccessTokenService
import com.spectrum.crimson.common.utils.JwtTokenProvider
import com.spectrum.crimson.domain.dto.MemberSignUpDto
import com.spectrum.crimson.domain.dto.SignInRequestDto
import com.spectrum.crimson.domain.entity.Member
import com.spectrum.crimson.domain.enums.MemberStatus
import com.spectrum.crimson.domain.enums.MsgKOR
import com.spectrum.crimson.domain.model.CrimsonToken
import com.spectrum.crimson.domain.redis.entity.RedisBlackAccessToken
import com.spectrum.crimson.domain.redis.entity.RedisRefreshToken
import com.spectrum.crimson.domain.repository.MemberRepository
import com.spectrum.crimson.service.redis.RedisRefreshTokenService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberAuthService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository,
    private val redisRefreshTokenService: RedisRefreshTokenService,
    private val redisBlackAccessTokenService: RedisBlackAccessTokenService,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
) {
    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    fun signIn(dto: SignInRequestDto): CrimsonToken {
        val authenticationToken = UsernamePasswordAuthenticationToken(dto.email, dto.password)
        val authentication: Authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        val jwtToken = jwtTokenProvider.generateToken(authentication)
        val key = jwtTokenProvider.getDataFromToken(jwtToken.accessToken, "email") as String
        val claims = jwtTokenProvider.parseClaims(jwtToken.refreshToken)
        val exp = claims["exp"] as Long
        val ttlSeconds = getTokenTtlSeconds(exp)

        val refreshToken = RedisRefreshToken(key, ttlSeconds.toInt(), jwtToken.refreshToken)
        redisRefreshTokenService.saveRefreshToken(refreshToken)

        return jwtToken
    }

    fun signUp(dto: MemberSignUpDto): Member {
        this.checkDuplicatedMemberUnique(dto)

        val encryptedPassword = passwordEncoder.encode(dto.password)

        return memberRepository.save(
            Member(
                name = dto.name,
                email = dto.email,
                phone = dto.phone,
                password = encryptedPassword,
                status = MemberStatus.ACTIVE,
                deletedAt = null,
            )
        )
    }

    fun reissueAccessToken(accessToken: String, refreshToken: String): CrimsonToken {
        verifiedRefreshToken(refreshToken)
        val rawAccessToken = jwtTokenProvider.removePrefix(accessToken)
        val rawRefreshToken = jwtTokenProvider.removePrefix(refreshToken)
        val key = jwtTokenProvider.getDataFromToken(refreshToken, "loginId") as String
        val redisRefreshTokenEntity = redisRefreshTokenService.getRefreshToken(key)
            ?: throw CrimsonException(MsgKOR.NOT_EXISTS_REFRESH_TOKEN.message)

        val redisRefreshToken = redisRefreshTokenEntity.value

        if (rawRefreshToken != redisRefreshToken) {
            throw CrimsonException(MsgKOR.INVALID_REFRESH_TOKEN_NOT_EXISTS.message)
        }

        // 기존 Access Token 블랙 리스트 추가
        val redisBlackAccessToken = RedisBlackAccessToken(rawAccessToken)
        redisBlackAccessTokenService.saveBlackAccessToken(redisBlackAccessToken)

        val authentication = jwtTokenProvider.getAuthentication(rawRefreshToken)
        val jwtToken = jwtTokenProvider.reissueToken(authentication, rawRefreshToken)

        // 신규 Refresh Token 화이트 리스트 추가
        val claims = jwtTokenProvider.parseClaims(jwtToken.refreshToken)
        val exp = claims["exp"] as Long
        val ttlSeconds = getTokenTtlSeconds(exp)
        val newRedisRefreshTokenEntity = RedisRefreshToken(key, ttlSeconds.toInt(), jwtToken.refreshToken)
        redisRefreshTokenService.saveRefreshToken(newRedisRefreshTokenEntity)

        return CrimsonToken(
            accessToken = jwtToken.accessToken,
            refreshToken = jwtToken.refreshToken
        )
    }

    private fun getTokenTtlSeconds(exp: Long): Long {
        val now = System.currentTimeMillis() / 1000
        return maxOf((exp - now), 0)
    }

    private fun verifiedRefreshToken(encryptedRefreshToken: String?) {
        requireNotNull(encryptedRefreshToken) {
            MsgKOR.NOT_EXISTS_REFRESH_TOKEN.message
        }
    }

    private fun checkDuplicatedMemberUnique(dto: MemberSignUpDto) {
        if (memberRepository.findByEmail(dto.email).isPresent) {
            throw CrimsonException(MsgKOR.ALREADY_EXIST_EMAIL.message)
        }

        if (memberRepository.findByPhone(dto.phone).isPresent) {
            throw CrimsonException(MsgKOR.ALREADY_EXIST_PHONE.message)
        }

        if (memberRepository.findByName(dto.name).isPresent) {
            throw CrimsonException(MsgKOR.ALREADY_EXIST_NAME.message)
        }
    }
}