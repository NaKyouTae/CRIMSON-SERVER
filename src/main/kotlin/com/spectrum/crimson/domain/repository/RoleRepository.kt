package com.spectrum.crimson.domain.repository

import com.spectrum.crimson.domain.entity.Role
import com.spectrum.crimson.domain.enums.RoleName
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RoleRepository : JpaRepository<Role, String> {
    fun findByName(name: RoleName): Optional<Role>
}
