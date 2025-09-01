package com.spectrum.crimson.service

import com.spectrum.crimson.domain.dto.MemberPlaceGroupCreateDto
import com.spectrum.crimson.domain.entity.MemberPlaceGroup
import com.spectrum.crimson.domain.repository.MemberPlaceGroupRepository
import org.springframework.stereotype.Service

@Service
class MemberPlaceGroupService(
    private val memberPlaceGroupRepository: MemberPlaceGroupRepository,
) {

    fun isMemberInGroup(memberId: String, itemGroupId: String): Boolean {
        return memberPlaceGroupRepository.findAllByMemberIdAndPlaceGroupId(memberId, itemGroupId).isPresent
    }

    fun createMemberPlaceGroup(dto: MemberPlaceGroupCreateDto): MemberPlaceGroup {
        val memberPlaceGroup = MemberPlaceGroup(
            placeGroup = dto.placeGroup,
            member = dto.member,
            role = dto.role,
        )

        return memberPlaceGroupRepository.save(memberPlaceGroup)
    }
}