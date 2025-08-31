package com.spectrum.crimson.domain.model

import com.spectrum.crimson.domain.entity.Member
import com.spectrum.crimson.domain.entity.MemberRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import kotlin.collections.map

data class CrimsonMemberDetail(
    private val member: Member,
    private val roles: List<MemberRole>,
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it.role.name.name) }
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