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
    name = "place_group",
    indexes = [
        Index(name = "idx_place_group_status", columnList = "status"),
    ]
)
class PlaceGroup(
    name: String,
    status: PlaceGroupStatus,
    description: String?,
): BaseEntity("IG") {
    @Column(name = "name", length = 2048, nullable = false)
    var name: String = name
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 32, nullable = false)
    var status: PlaceGroupStatus = status
        protected set

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    var description: String? = description
        protected set
}