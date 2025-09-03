package com.spectrum.crimson.domain.entity

import com.spectrum.crimson.domain.enums.PlaceGroupStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "place",
    indexes = [
        Index(name = "idx_place_group_status", columnList = "status"),
    ]
)
class Place(
    name: String,
    status: PlaceGroupStatus,
    lng: String,
    lat: String,
): BaseEntity("PL") {

    @Column(name = "placa_id", length = 32, nullable = false)
    var placeLocationId: String = placeLocationId
        protected set

    @Column(name = "name", length = 2048, nullable = false)
    var name: String = name
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 32, nullable = false)
    var status: PlaceGroupStatus = status
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 32, nullable = false)
    var category: PlaceGroupStatus = category
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
}