package com.spectrum.crimson.domain.repository

import com.spectrum.crimson.domain.entity.PlaceGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface PlaceGroupRepository : JpaRepository<PlaceGroup, String> {
    @Query(
        """
        select ig
        from PlaceGroup ig
        join MemberPlaceGroup mig on mig.placeGroup = ig
        where ig.id = :placeGroupId and mig.member.id = :memberId
    """
    )
    fun findAccessibleById(memberId: String, placeGroupId: String): Optional<PlaceGroup>
}
