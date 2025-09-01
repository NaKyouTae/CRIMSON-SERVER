package com.spectrum.crimson.domain.repository

import com.spectrum.crimson.domain.entity.ItemGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ItemGroupRepository : JpaRepository<ItemGroup, String> {
    @Query("""
        select ig
        from ItemGroup ig
        join MemberItemGroup mig on mig.itemGroup = ig
        where ig.id = :itemGroupId and mig.member.id = :memberId
    """)
    fun findAccessibleById(memberId: String, itemGroupId: String): Optional<ItemGroup>
}
