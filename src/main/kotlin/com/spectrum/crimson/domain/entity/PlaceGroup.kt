package com.spectrum.crimson.domain.entity

import com.spectrum.crimson.domain.enums.PlaceGroupCategory
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
    icon: String,
    name: String,
    status: PlaceGroupStatus,
    category: PlaceGroupCategory,
    memo: String?,
    link: String?,
): BaseEntity("PG") {
    @Column(name = "icon", length = 128, nullable = false)
    var icon: String = icon
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
    var category: PlaceGroupCategory = category
        protected set

    @Column(name = "memo", columnDefinition = "TEXT", nullable = true)
    var memo: String? = memo
        protected set

    @Column(name = "link", columnDefinition = "TEXT", nullable = true)
    var link: String? = link
        protected set
}