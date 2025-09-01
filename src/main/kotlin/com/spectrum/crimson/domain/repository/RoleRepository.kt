package com.spectrum.crimson.domain.repository

import com.spectrum.crimson.domain.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, String> {}
