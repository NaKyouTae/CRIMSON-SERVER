package com.spectrum.crimson.domain.repository

import com.spectrum.crimson.domain.entity.MemberItemGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface MemberItemGroupRepository : JpaRepository<MemberItemGroup, String> {
    fun findAllByMemberIdAndItemGroupId(memberId: String, itemGroupId: String): Optional<MemberItemGroup>
}
