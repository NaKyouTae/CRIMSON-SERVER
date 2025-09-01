package com.spectrum.crimson.domain.repository

import com.spectrum.crimson.domain.entity.MemberItemGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberItemGroupRepository : JpaRepository<MemberItemGroup, String> {}
