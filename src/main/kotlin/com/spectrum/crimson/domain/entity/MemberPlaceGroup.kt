package com.spectrum.crimson.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(
    name = "member_place_group",
    uniqueConstraints = [
        UniqueConstraint(name = "uq_mig_member_place_group_role", columnNames = ["member_id", "place_group_id", "role_id"])
    ],
    indexes = [
        Index(name = "idx_mig_member_id", columnList = "member_id"),
        Index(name = "idx_mig_role_id", columnList = "role_id"),
        Index(name = "idx_mig_place_group_id", columnList = "place_group_id"),
        Index(name = "idx_mig_member_id_role_id", columnList = "member_id, role_id"),
    ]
)
class MemberPlaceGroup(
    placeGroup: PlaceGroup,
    member: Member,
    role: Role,
) : BaseEntity("MR") {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_group_id", nullable = false)
    var placeGroup: PlaceGroup = placeGroup
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member = member
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    var role: Role = role
        protected set
}