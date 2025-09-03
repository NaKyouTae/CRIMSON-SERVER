package com.spectrum.crimson.domain.dto

import com.spectrum.crimson.domain.enums.PlaceGroupCategory
import com.spectrum.crimson.domain.enums.PlaceGroupStatus

data class PlaceGroupCreateDto(
    val name: String,
    val status: PlaceGroupStatus,
    val category: PlaceGroupCategory,
    val memo: String?,
    val link: String?,
    val memberId: String,
)
