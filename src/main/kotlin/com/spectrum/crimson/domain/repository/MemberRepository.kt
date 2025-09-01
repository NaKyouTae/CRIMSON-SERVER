package com.spectrum.crimson.domain.repository

import com.spectrum.crimson.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface MemberRepository: JpaRepository<Member, String> {
    fun findByEmail(email: String): Optional<Member>
    fun findByPhone(phone: String): Optional<Member>
    fun findByName(name: String): Optional<Member>

    @Query("""
        SELECT m FROM Member m
        LEFT JOIN FETCH m.mutableMemberItemGroups mig
        LEFT JOIN FETCH mig.itemGroup
        WHERE m.id = :memberId
    """)
    fun findByIdWithItemGroups(memberId: String): Optional<Member>
}