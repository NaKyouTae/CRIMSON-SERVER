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
    name = "place_group_place",
    indexes = [
        Index(name = "idx_place_group_place_place_id", columnList = "place_id"),
        Index(name = "idx_place_group_place_place_group_id", columnList = "place_group_id"),
    ]
)
class PlaceGroupPlace(
    place: Place,
    placeGroup: PlaceGroup,
): BaseEntity("PL") {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    var place: Place = place
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_group_id", nullable = false)
    var placeGroup: PlaceGroup = placeGroup
        protected set
}