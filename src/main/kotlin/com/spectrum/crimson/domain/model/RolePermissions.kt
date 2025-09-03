package com.spectrum.crimson.domain.model

import jakarta.persistence.Embeddable

@Embeddable
data class RolePermissions(
    val canCreate: Boolean = false,
    val canEdit: Boolean = false,
    val canDelete: Boolean = false,
    val canView: Boolean = true
)