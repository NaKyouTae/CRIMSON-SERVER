package com.spectrum.crimson.domain.repository

import com.spectrum.crimson.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository: JpaRepository<Member, String> {
}