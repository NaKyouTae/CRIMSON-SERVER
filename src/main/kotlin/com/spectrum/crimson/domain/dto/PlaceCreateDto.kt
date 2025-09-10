package com.spectrum.crimson.domain.dto

import com.spectrum.crimson.domain.entity.PlaceGroup
import com.spectrum.crimson.domain.enums.PlaceGroupCategory
import com.spectrum.crimson.domain.enums.PlaceGroupStatus
import com.spectrum.crimson.domain.enums.PlaceStatus

data class PlaceCreateDto(
    val locationId: String,
    val name: String,
    val status: PlaceStatus,
    val categoryName: String,
    val addressName: String,
    val phone: String,
    val url: String,
    val lng: String,
    val lat: String,
    val placeGroup: PlaceGroup,
)
