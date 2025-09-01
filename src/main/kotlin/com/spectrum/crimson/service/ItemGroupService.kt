package com.spectrum.crimson.service

import com.spectrum.crimson.common.exception.CrimsonException
import com.spectrum.crimson.domain.dto.ItemGroupCreateDto
import com.spectrum.crimson.domain.dto.MemberItemGroupCreateDto
import com.spectrum.crimson.domain.entity.ItemGroup
import com.spectrum.crimson.domain.entity.Role
import com.spectrum.crimson.domain.enums.RoleName
import com.spectrum.crimson.domain.repository.ItemGroupRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemGroupService(
    private val roleService: RoleService,
    private val memberService: MemberService,
    private val itemGroupRepository: ItemGroupRepository,
    private val memberItemGroupService: MemberItemGroupService,
) {

    @Transactional
    fun createItemGroup(dto: ItemGroupCreateDto): ItemGroup {
        val member = memberService.getMember(dto.memberId) ?: throw CrimsonException("존재하지 않는 멤버입니다.")

        val itemGroup = ItemGroup(
            name = dto.name,
            status = dto.status,
            description = dto.description,
        )
        val captainRole = roleService.getRoleByName(RoleName.CAPTAIN)
        val createdItemGroup = itemGroupRepository.save(itemGroup)

        val memberItemGroupDto = MemberItemGroupCreateDto(
            itemGroup = createdItemGroup,
            member = member,
            role = captainRole,
        )

        memberItemGroupService.createMemberItemGroup(memberItemGroupDto)

        return createdItemGroup
    }
}