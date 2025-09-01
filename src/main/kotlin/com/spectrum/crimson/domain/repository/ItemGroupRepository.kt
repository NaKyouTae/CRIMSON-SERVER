package com.spectrum.crimson.domain.repository

import com.spectrum.crimson.domain.entity.ItemGroup
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemGroupRepository : JpaRepository<ItemGroup, String> {}
