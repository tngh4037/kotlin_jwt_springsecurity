package com.example.demo.member.repository

import com.example.demo.member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByLoginId(loginId: String): Member?  // Member 객체를 반환하거나, 결과가 없으면 null을 반환.
}