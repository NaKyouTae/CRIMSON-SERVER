package com.spectrum.crimson.common.service

import com.spectrum.crimson.common.exception.SpectrumException
import com.spectrum.crimson.domain.enums.MsgKOR
import com.spectrum.crimson.domain.model.SpectrumMemberDetail
import com.spectrum.crimson.domain.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberDetailsService(
    private val memberRepository: MemberRepository,
) : UserDetailsService {

    @Transactional(readOnly = true)
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(id: String): UserDetails {
        val foundMember = memberRepository.findById(id)

        if(foundMember.isPresent) {
            val member = foundMember.get()
            return SpectrumMemberDetail(member, member.roles)
        }

        throw SpectrumException(MsgKOR.NOT_FOUND_USER.message)
    }
}