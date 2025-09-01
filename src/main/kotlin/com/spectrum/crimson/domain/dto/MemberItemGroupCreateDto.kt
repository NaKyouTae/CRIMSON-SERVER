package com.spectrum.crimson.domain.dto

import com.spectrum.crimson.domain.entity.ItemGroup
import com.spectrum.crimson.domain.entity.Member
import com.spectrum.crimson.domain.entity.Role

data class MemberItemGroupCreateDto(
    val itemGroup: ItemGroup,
    val member: Member,
    val role: Role,
)
