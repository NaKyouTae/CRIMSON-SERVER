package com.spectrum.crimson.service

import com.spectrum.crimson.common.exception.CrimsonException
import com.spectrum.crimson.domain.entity.Role
import com.spectrum.crimson.domain.enums.MsgKOR
import com.spectrum.crimson.domain.enums.RoleName
import com.spectrum.crimson.domain.repository.RoleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RoleService(
    private val roleRepository: RoleRepository,
) {

    @Transactional
    fun getRoleByName(name: RoleName): Role {
        return roleRepository.findByName(name).orElseThrow { throw CrimsonException(MsgKOR.NOT_FOUND_CAPTAIN_ROLE.message) }
    }
}