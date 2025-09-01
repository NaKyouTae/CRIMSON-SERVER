package com.spectrum.crimson.domain.dto

import com.spectrum.crimson.domain.entity.PlaceGroup
import com.spectrum.crimson.domain.entity.Member
import com.spectrum.crimson.domain.entity.Role

data class MemberPlaceGroupCreateDto(
    val placeGroup: PlaceGroup,
    val member: Member,
    val role: Role,
)
