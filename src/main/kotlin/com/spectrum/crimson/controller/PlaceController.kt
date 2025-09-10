package com.spectrum.crimson.controller

import com.spectrum.crimson.config.resolver.model.AccessToken
import com.spectrum.crimson.domain.extension.toProto
import com.spectrum.crimson.domain.model.AccessTokenInfo
import com.spectrum.crimson.proto.place.PlaceCreateRequest
import com.spectrum.crimson.proto.place.PlaceCreateResult
import com.spectrum.crimson.proto.place.PlaceListResult
import com.spectrum.crimson.service.PlaceService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/places")
class PlaceController(
    private val placeService: PlaceService,
) {
    @GetMapping("/{placeGroupId}")
    fun listPlaces(
        @PathVariable placeGroupId: String,
    ): PlaceListResult {
        val places = placeService.listPlacesAsProto(placeGroupId)
        return PlaceListResult.newBuilder().addAllPlaces(places).build()
    }

    @PostMapping
    fun addPlace(
        @AccessToken tokenInfo: AccessTokenInfo,
        @Valid @RequestBody request: PlaceCreateRequest,
    ): PlaceCreateResult {
        val place = placeService.createPlace(tokenInfo.id, request)

        return PlaceCreateResult.newBuilder().setId(place.id).build()
    }
}