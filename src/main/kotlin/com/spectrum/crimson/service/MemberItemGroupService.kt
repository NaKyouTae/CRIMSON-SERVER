package com.spectrum.crimson.service

import com.spectrum.crimson.domain.dto.MemberItemGroupCreateDto
import com.spectrum.crimson.domain.entity.MemberItemGroup
import com.spectrum.crimson.domain.repository.MemberItemGroupRepository
import org.springframework.stereotype.Service

@Service
class MemberItemGroupService(
    private val memberItemGroupRepository: MemberItemGroupRepository,
) {

    fun createMemberItemGroup(dto: MemberItemGroupCreateDto): MemberItemGroup {
        val memberItemGroup = MemberItemGroup(
            itemGroup = dto.itemGroup,
            member = dto.member,
            role = dto.role,
        )

        return memberItemGroupRepository.save(memberItemGroup)
    }
}