package com.spectrum.crimson.service

import com.spectrum.crimson.common.exception.CrimsonException
import com.spectrum.crimson.domain.entity.Place
import com.spectrum.crimson.domain.enums.MsgKOR
import com.spectrum.crimson.domain.enums.PlaceStatus
import com.spectrum.crimson.domain.extension.toProto
import com.spectrum.crimson.domain.repository.PlaceRepository
import com.spectrum.crimson.proto.place.PlaceCreateRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaceService(
    private val memberService: MemberService,
    private val placeRepository: PlaceRepository,
    private val placeGroupService: PlaceGroupService,
    private val memberPlaceGroupService: MemberPlaceGroupService,
) {

    @Transactional(readOnly = true)
    fun listPlaces(placeGroupId: String): List<Place> {
        return placeRepository.findByPlaceGroupId(placeGroupId)
    }

    @Transactional(readOnly = true)
    fun listPlacesAsProto(placeGroupId: String): List<com.spectrum.crimson.proto.Place> {
        val places = placeRepository.findByPlaceGroupId(placeGroupId)
        return places.map { it.toProto() }
    }

    @Transactional
    fun createPlace(memberId: String, request: PlaceCreateRequest): Place {
        val member = memberService.getMember(memberId)
        val placeGroup = placeGroupService.getPlaceGroup(memberId, request.placeGroupId)
        val isMemberPlaceGroup = memberPlaceGroupService.isMemberInGroup(memberId, request.placeGroupId)

        val place = Place(
            locationId = request.place.id,
            name = request.place.name,
            status = PlaceStatus.ACTIVE,
            categoryName = request.place.categoryGroupName,
            addressName = request.place.addressName,
            phone = request.place.phone,
            url = request.place.url,
            lng = request.place.x,
            lat = request.place.y,
            placeGroup = placeGroup,
            member = member,
        )

        return placeRepository.save(place)
    }
}