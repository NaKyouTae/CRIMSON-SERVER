package com.spectrum.crimson.domain.dto

import com.spectrum.crimson.domain.enums.ItemGroupStatus

data class ItemGroupCreateDto(
    val name: String,
    val status: ItemGroupStatus,
    val description: String?,
    val memberId: String,
)
