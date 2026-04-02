package com.example.demo.member.entity

import com.example.demo.common.status.Gender
import com.example.demo.member.dto.MemberDtoResponse
import jakarta.persistence.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["loginId"])] // loginId 가 중복되지 않도록 unique key 설정
)
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, length = 30, updatable = false) // Member 에 update 가 발생할 때, loginId 는 제외시킴으로써 변경되지 않도록 설정
    val loginId: String,

    @Column(nullable = false, length = 100)
    val password: String,

    @Column(nullable = false, length = 10)
    val name: String,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE) // 날짜만 입력되도록 설정
    val birthDate: LocalDate,

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING) // Enum 의 name 으로 데이터베이스에 저장되도록 설정
    val gender: Gender,

    @Column(nullable = false, length = 30)
    val email: String,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    val memberRole: List<MemberRole>? = null
) {

    private fun LocalDate.formatDate(): String =
        this.format(DateTimeFormatter.ofPattern("yyyyMMdd"))

    fun toDto(): MemberDtoResponse =
        MemberDtoResponse(id!!, loginId, name, birthDate.formatDate(), gender.desc, email)
}
