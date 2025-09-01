package com.spectrum.crimson.domain.repository

import com.spectrum.crimson.domain.entity.MemberPlaceGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface MemberPlaceGroupRepository : JpaRepository<MemberPlaceGroup, String> {
    fun findAllByMemberIdAndPlaceGroupId(memberId: String, placeGroupId: String): Optional<MemberPlaceGroup>
}
