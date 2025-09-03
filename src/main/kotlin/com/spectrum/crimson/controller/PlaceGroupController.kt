package com.spectrum.crimson.controller

import com.spectrum.crimson.config.resolver.model.AccessToken
import com.spectrum.crimson.domain.dto.PlaceGroupCreateDto
import com.spectrum.crimson.domain.enums.PlaceGroupCategory
import com.spectrum.crimson.domain.enums.PlaceGroupStatus
import com.spectrum.crimson.domain.extension.toProto
import com.spectrum.crimson.domain.model.AccessTokenInfo
import com.spectrum.crimson.proto.place_group.PlaceGroupCreateRequest
import com.spectrum.crimson.proto.place_group.PlaceGroupCreateResponse
import com.spectrum.crimson.proto.place_group.PlaceGroupListResult
import com.spectrum.crimson.proto.place_group.PlaceGroupResult
import com.spectrum.crimson.service.PlaceGroupService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/place-groups")
class PlaceGroupController(
    private val placeGroupService: PlaceGroupService,
) {

    @GetMapping("/{placeGroupId}")
    fun getPlaceGroup(
        @AccessToken tokenInfo: AccessTokenInfo,
        @PathVariable placeGroupId: String
    ): PlaceGroupResult {
        val placeGroup = placeGroupService.getPlaceGroup(tokenInfo.id, placeGroupId)
        return PlaceGroupResult.newBuilder().setGroup(placeGroup.toProto()).build()
    }

    @GetMapping()
    fun getPlaceGroups(@AccessToken tokenInfo: AccessTokenInfo): PlaceGroupListResult {
        val placeGroups = placeGroupService.getPlaceGroups(tokenInfo.id)
        return PlaceGroupListResult.newBuilder().addAllGroups(placeGroups.placeGroups).build()
    }

    @PostMapping
    fun createPlaceGroup(
        @AccessToken tokenInfo: AccessTokenInfo,
        @Valid @RequestBody request: PlaceGroupCreateRequest
    ): PlaceGroupCreateResponse {
        val placeGroupDto = PlaceGroupCreateDto(
            name = request.name,
            status = PlaceGroupStatus.valueOf(request.status.name),
            category = PlaceGroupCategory.valueOf(request.category.name),
            memo = request.memo,
            link = request.link,
            memberId = tokenInfo.id,
        )

        val placeGroup = placeGroupService.createPlaceGroup(placeGroupDto)
        return PlaceGroupCreateResponse.newBuilder().setId(placeGroup.id).build()
    }
}