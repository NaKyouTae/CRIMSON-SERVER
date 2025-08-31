package com.spectrum.crimson.domain.entity

import com.spectrum.crimson.domain.enums.RoleName
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "role")
class Role(
    name: RoleName,
): BaseEntity ("RO") {
    @Column(name = "name", length = 1024, nullable = false)
    var name: RoleName = name
        protected set
}