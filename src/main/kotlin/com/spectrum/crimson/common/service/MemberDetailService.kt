package com.spectrum.crimson.common.service

import com.spectrum.crimson.common.exception.CrimsonException
import com.spectrum.crimson.domain.enums.MsgKOR
import com.spectrum.crimson.domain.model.CrimsonMemberDetail
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
    override fun loadUserByUsername(email: String): UserDetails {
        val foundMember = memberRepository.findByEmail(email)

        if(foundMember.isPresent) {
            val member = foundMember.get()
            return CrimsonMemberDetail(member)
        }

        throw CrimsonException(MsgKOR.NOT_FOUND_USER.message)
    }
}