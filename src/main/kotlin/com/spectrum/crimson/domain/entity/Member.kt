package com.spectrum.crimson.domain.entity

import com.spectrum.crimson.domain.enums.MemberStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Index
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(
    name = "member",
    indexes = [
        Index(name = "idx_member_status", columnList = "status"),
        Index(name = "idx_member_email", columnList = "email", unique = true),
        Index(name = "idx_member_phone", columnList = "phone", unique = true),
    ]
)
class Member(
    name: String,
    email: String,
    phone: String,
    password: String,
    status: MemberStatus,
    deletedAt: LocalDateTime? = null,
): BaseEntity("ME") {
    @Column(name = "name", length = 16, nullable = false)
    var name: String = name
        protected set

    @Column(name = "password", length = 128, nullable = false)
    var password: String = password
        protected set

    @Column(name = "email", length = 32, nullable = false, unique = true)
    var email: String = email
        protected set

    @Column(name = "phone", length = 16, nullable = false, unique = true)
    var phone: String = phone
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 32, nullable = false)
    var status: MemberStatus = status
        protected set

    @Column(name = "deleted_at", nullable = true)
    var deletedAt: LocalDateTime? = deletedAt
        protected set

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private var mutableMemberPlaceGroups: MutableList<MemberPlaceGroup> = mutableListOf()
    val memberPlaceGroups: List<MemberPlaceGroup> get() = mutableMemberPlaceGroups.toList()
}