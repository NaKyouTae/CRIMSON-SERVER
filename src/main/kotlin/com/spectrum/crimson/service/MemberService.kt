package com.spectrum.crimson.service

import com.spectrum.crimson.common.exception.CrimsonException
import com.spectrum.crimson.domain.entity.Member
import com.spectrum.crimson.domain.enums.MsgKOR
import com.spectrum.crimson.domain.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {

    @Transactional(readOnly = true)
    fun getMember(id: String): Member {
        return memberRepository.findById(id).orElseThrow { throw CrimsonException(MsgKOR.NOT_FOUND_USER.message) }
    }

    @Transactional(readOnly = true)
    fun findByMemberWithPlaceGroups(id: String): Member {
        return memberRepository.findByIdWithPlaceGroups(id).orElseThrow { throw CrimsonException(MsgKOR.NOT_FOUND_USER.message) }
    }
}