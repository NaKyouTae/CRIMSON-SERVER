package com.spectrum.crimson.service

import com.spectrum.crimson.common.exception.CrimsonException
import com.spectrum.crimson.domain.dto.ItemGroupCreateDto
import com.spectrum.crimson.domain.dto.ItemGroupListDto
import com.spectrum.crimson.domain.dto.MemberItemGroupCreateDto
import com.spectrum.crimson.domain.entity.ItemGroup
import com.spectrum.crimson.domain.enums.MsgKOR
import com.spectrum.crimson.domain.enums.RoleName
import com.spectrum.crimson.domain.extension.toProto
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

    @Transactional(readOnly = true)
    fun getItemGroup(memberId: String, itemGroupId: String): ItemGroup {
        return itemGroupRepository.findAccessibleById(memberId, itemGroupId)
            .orElseThrow { CrimsonException(MsgKOR.NOT_FOUND_ITEM_GROUP.message) }
    }

    @Transactional(readOnly = true)
    fun getItemGroups(memberId: String): ItemGroupListDto {
        val member = memberService.getMember(memberId)
        val memberItemGroup = member.memberItemGroups
        val itemGroups = memberItemGroup.map { it.itemGroup }

        return ItemGroupListDto(
            itemGroups = itemGroups.map { it.toProto() }
        )
    }

//    @Transactional(readOnly = true)
//    fun getItemGroups(memberId: String): List<ItemGroup> {
//        val member = memberService.findByMemberWithItemGroups(memberId)
//        val memberItemGroup = member.memberItemGroups
//        val itemGroups = memberItemGroup.map { it.itemGroup }
//
//        return itemGroups
//    }

//    @Transactional(readOnly = true)
//    fun getItemGroup(memberId: String, itemGroupId: String): ItemGroup {
//        val isMemberInGroup = memberItemGroupService.isMemberInGroup(memberId, itemGroupId)
//
//        if (!isMemberInGroup) {
//            throw CrimsonException(MsgKOR.UNAUTHORIZED_ITEM_GROUP_ACCESS.message)
//        }
//
//        return itemGroupRepository.findById(itemGroupId)
//            .orElseThrow { throw CrimsonException(MsgKOR.NOT_FOUND_ITEM_GROUP.message) }
//    }

    @Transactional
    fun createItemGroup(dto: ItemGroupCreateDto): ItemGroup {
        val member = memberService.getMember(dto.memberId) ?: throw CrimsonException(MsgKOR.NOT_FOUND_USER.message)

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