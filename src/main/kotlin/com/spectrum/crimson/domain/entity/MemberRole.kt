package com.spectrum.crimson.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(
    name = "member_role",
    indexes = [
        Index(name = "idx_member_role_member_id", columnList = "member_id"),
        Index(name = "idx_member_role_role_id", columnList = "role_id"),
        Index(name = "idx_member_role_member_id_role_id", columnList = "member_id, role_id"),
    ]
)
class MemberRole(
    member: Member,
    role: Role,
) : BaseEntity("MR") {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member = member
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    var role: Role = role
        protected set
}