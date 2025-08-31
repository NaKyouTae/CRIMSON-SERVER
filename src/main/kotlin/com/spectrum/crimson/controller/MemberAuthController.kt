package com.spectrum.crimson.controller

import com.spectrum.crimson.common.exception.CrimsonException
import com.spectrum.crimson.domain.dto.MemberSignUpDto
import com.spectrum.crimson.domain.dto.SignInRequestDto
import com.spectrum.crimson.proto.MemberSignInRequest
import com.spectrum.crimson.proto.MemberSignInResult
import com.spectrum.crimson.proto.MemberSignUpRequest
import com.spectrum.crimson.proto.MemberSignUpResult
import com.spectrum.crimson.service.MemberAuthService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth/members")
class MemberAuthController(
    private val memberAuthService: MemberAuthService,
) {

    @PostMapping("/signin")
    fun signIn(@Valid @RequestBody request: MemberSignInRequest): MemberSignInResult {
        val signInDto = SignInRequestDto(
            email = request.email,
            password = request.password,
        )

        val token = memberAuthService.signIn(signInDto)

        return MemberSignInResult.newBuilder().setToken(token.toProto()).build()
    }

    @PostMapping("/signup")
    fun signUp(@Valid @RequestBody request: MemberSignUpRequest): MemberSignUpResult {
        val dto = MemberSignUpDto(
            name = request.name,
            email = request.email,
            phone = request.phone,
            password = request.password,
        )

        validateMemeberSignUpRequest(dto)

        val member = memberAuthService.signUp(dto)
        return MemberSignUpResult.newBuilder().setId(member.id).setName(member.name).build()
    }

    private fun validateMemeberSignUpRequest(dto: MemberSignUpDto) {
        if(dto.name.isEmpty()) { throw CrimsonException("필수값인 name이 없습니다.") }
        if(dto.email.isEmpty()) { throw CrimsonException("필수값인 email이 없습니다.") }
        if(dto.phone.isEmpty()) { throw CrimsonException("필수값인 phone이 없습니다.") }
        if(dto.password.isEmpty()) { throw CrimsonException("필수값인 password가 없습니다.") }
    }
}