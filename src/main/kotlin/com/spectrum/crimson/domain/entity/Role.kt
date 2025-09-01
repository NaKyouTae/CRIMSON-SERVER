package com.spectrum.crimson.domain.entity

import com.spectrum.crimson.common.converter.RolePermissionsJsonbConverter
import com.spectrum.crimson.domain.enums.RoleName
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "role",
    indexes = [
        Index(name = "idx_role_name", columnList = "name", unique = true)
    ]
)
class Role(
    name: RoleName,
    permissions: RolePermissions,
): BaseEntity ("RO") {

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 1024, nullable = false)
    var name: RoleName = name
        protected set

    @Convert(converter = RolePermissionsJsonbConverter::class)
    @Column(columnDefinition = "jsonb", nullable = false)
    var permissions: RolePermissions = permissions
        protected set
}