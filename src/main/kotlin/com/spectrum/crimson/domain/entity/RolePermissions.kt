package com.spectrum.crimson.domain.entity

import jakarta.persistence.Embeddable

@Embeddable
data class RolePermissions(
    val canCreate: Boolean = false,
    val canEdit: Boolean = false,
    val canDelete: Boolean = false,
    val canView: Boolean = true
)