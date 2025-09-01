package com.spectrum.crimson.domain.model

import com.spectrum.crimson.domain.entity.Member
import com.spectrum.crimson.domain.entity.MemberItemGroup
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import kotlin.collections.map

data class CrimsonMemberDetail(
    private val member: Member,
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return emptyList()
    }

    override fun getPassword(): String {
        return member.password
    }

    override fun getUsername(): String {
        return member.name
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun getMemberId(): String = member.id
    fun getEmail(): String = member.email
}