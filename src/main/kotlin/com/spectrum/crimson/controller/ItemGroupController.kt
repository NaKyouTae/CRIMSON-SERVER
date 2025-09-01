package com.spectrum.crimson.controller

import com.spectrum.crimson.config.resolver.model.AccessToken
import com.spectrum.crimson.domain.dto.ItemGroupCreateDto
import com.spectrum.crimson.domain.enums.ItemGroupStatus
import com.spectrum.crimson.domain.extension.toProto
import com.spectrum.crimson.domain.model.AccessTokenInfo
import com.spectrum.crimson.proto.item_group.ItemGroupCreateRequest
import com.spectrum.crimson.proto.item_group.ItemGroupCreateResponse
import com.spectrum.crimson.proto.item_group.ItemGroupListResult
import com.spectrum.crimson.proto.item_group.ItemGroupResult
import com.spectrum.crimson.service.ItemGroupService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/item-groups")
class ItemGroupController(
    private val itemGroupService: ItemGroupService,
) {

    @GetMapping("/{itemGroupId}")
    fun getItemGroup(
        @AccessToken tokenInfo: AccessTokenInfo,
        @PathVariable itemGroupId: String
    ): ItemGroupResult {
        val itemGroup = itemGroupService.getItemGroup(tokenInfo.id, itemGroupId)
        return ItemGroupResult.newBuilder().setGroup(itemGroup.toProto()).build()
    }

    @GetMapping()
    fun getItemGroups(@AccessToken tokenInfo: AccessTokenInfo): ItemGroupListResult {
        val itemGroups = itemGroupService.getItemGroups(tokenInfo.id)
        return ItemGroupListResult.newBuilder().addAllGroups(itemGroups.itemGroups).build()
    }

    @PostMapping
    fun createItemGroup(
        @AccessToken tokenInfo: AccessTokenInfo,
        @Valid @RequestBody request: ItemGroupCreateRequest
    ): ItemGroupCreateResponse {
        val itemGroupDto = ItemGroupCreateDto(
            name = request.name,
            status = ItemGroupStatus.valueOf(request.status.name),
            description = request.description,
            memberId = tokenInfo.id,
        )

        val itemGroup = itemGroupService.createItemGroup(itemGroupDto)
        return ItemGroupCreateResponse.newBuilder().setId(itemGroup.id).build()
    }
}