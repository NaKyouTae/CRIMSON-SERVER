package com.spectrum.crimson.domain.entity

import com.spectrum.crimson.domain.enums.PlaceGroupStatus
import com.spectrum.crimson.domain.enums.PlaceStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(
    name = "place",
    indexes = [
        Index(name = "idx_place_status", columnList = "status"),
        Index(name = "idx_place_location_id", columnList = "location_id"),
        Index(name = "idx_place_place_group_id", columnList = "place_group_id"),
//        Index(name = "idx_place_name", columnList = "name"), // TEXT INDEX 필요
    ]
)
class Place(
    locationId: String,
    name: String,
    status: PlaceStatus,
    categoryName: String,
    addressName: String,
    phone: String,
    url: String,
    lng: String,
    lat: String,
    placeGroup: PlaceGroup,
    member: Member,
): BaseEntity("PL") {

    @Column(name = "location_id", length = 32, nullable = false)
    var locationId: String = locationId
        protected set

    @Column(name = "name", length = 2048, nullable = false)
    var name: String = name
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 32, nullable = false)
    var status: PlaceStatus = status
        protected set

    @Column(name = "category_name", columnDefinition = "TEXT", nullable = false)
    var categoryName: String = categoryName
        protected set

    @Column(name = "address_name", length = 512, nullable = false)
    var addressName: String = addressName
        protected set

    @Column(name = "phone", length = 32, nullable = false)
    var phone: String = phone
        protected set

    @Column(name = "url", columnDefinition = "TEXT", nullable = false)
    var url: String = url
        protected set

    @Column(name = "lng", length = 512, nullable = false)
    var lng: String = lng
        protected set

    @Column(name = "lat", length = 512, nullable = false)
    var lat: String = lat
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_group_id", nullable = false)
    var placeGroup: PlaceGroup = placeGroup
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member = member
        protected set
}