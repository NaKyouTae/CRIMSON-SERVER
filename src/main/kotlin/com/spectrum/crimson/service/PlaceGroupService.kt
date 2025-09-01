package com.spectrum.crimson.service

import com.spectrum.crimson.common.exception.CrimsonException
import com.spectrum.crimson.domain.dto.PlaceGroupCreateDto
import com.spectrum.crimson.domain.dto.PlaceGroupListDto
import com.spectrum.crimson.domain.dto.MemberPlaceGroupCreateDto
import com.spectrum.crimson.domain.entity.PlaceGroup
import com.spectrum.crimson.domain.enums.MsgKOR
import com.spectrum.crimson.domain.enums.RoleName
import com.spectrum.crimson.domain.extension.toProto
import com.spectrum.crimson.domain.repository.PlaceGroupRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaceGroupService(
    private val roleService: RoleService,
    private val memberService: MemberService,
    private val placeGroupRepository: PlaceGroupRepository,
    private val memberPlaceGroupService: MemberPlaceGroupService,
) {

    @Transactional(readOnly = true)
    fun getPlaceGroup(memberId: String, placeGroupId: String): PlaceGroup {
        return placeGroupRepository.findAccessibleById(memberId, placeGroupId)
            .orElseThrow { CrimsonException(MsgKOR.NOT_FOUND_PLACE_GROUP.message) }
    }

    @Transactional(readOnly = true)
    fun getPlaceGroups(memberId: String): PlaceGroupListDto {
        val member = memberService.getMember(memberId)
        val memberPlaceGroup = member.memberPlaceGroups
        val placeGroups = memberPlaceGroup.map { it.placeGroup }

        return PlaceGroupListDto(
            placeGroups = placeGroups.map { it.toProto() }
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
    fun createPlaceGroup(dto: PlaceGroupCreateDto): PlaceGroup {
        val member = memberService.getMember(dto.memberId) ?: throw CrimsonException(MsgKOR.NOT_FOUND_USER.message)

        val placeGroup = PlaceGroup(
            name = dto.name,
            status = dto.status,
            description = dto.description,
        )
        val captainRole = roleService.getRoleByName(RoleName.CAPTAIN)
        val createdPlaceGroup = placeGroupRepository.save(placeGroup)

        val memberItemGroupDto = MemberPlaceGroupCreateDto(
            placeGroup = createdPlaceGroup,
            member = member,
            role = captainRole,
        )

        memberPlaceGroupService.createMemberPlaceGroup(memberItemGroupDto)

        return createdPlaceGroup
    }
}