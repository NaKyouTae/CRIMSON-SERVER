package com.spectrum.crimson.domain.dto

import com.spectrum.crimson.domain.enums.PlaceGroupStatus

data class PlaceGroupCreateDto(
    val name: String,
    val status: PlaceGroupStatus,
    val description: String?,
    val memberId: String,
)
