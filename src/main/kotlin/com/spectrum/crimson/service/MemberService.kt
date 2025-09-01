package com.spectrum.crimson.service

import com.spectrum.crimson.domain.entity.Member
import com.spectrum.crimson.domain.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {

    @Transactional
    fun getMember(id: String): Member? {
        return memberRepository.findById(id).getOrNull()
    }
}