package com.example.demo.member.dto

import com.example.demo.common.annotation.ValidEnum
import com.example.demo.common.status.Gender
import com.example.demo.member.entity.Member
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class MemberDtoRequest(
    val id: Long?,

    @field: NotBlank
    @JsonProperty("loginId") // 사용자로부터는 _loginId로 요청이 오지 않는다 loginId로 온다. 따라서, loginId로 오면 _loginId와 연결되도록 해준다.
    private val _loginId: String?,

    @field: NotBlank
    @field:Pattern( // 영문/숫자/특수문자 8 ~ 20자리
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$",
        message = "영문, 숫자, 특수문자를 포함한 8~20자리로 입력해주세요"
    )
    @JsonProperty("password")
    private val _password: String?,

    @field: NotBlank
    @JsonProperty("name")
    private val _name: String?,

    @field: NotBlank
    @field:Pattern(
        regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
        message = "날짜형식(YYYY-MM-DD)을 확인해주세요"
    )
    @JsonProperty("birthDate")
    private val _birthDate: String?,

    @field: NotBlank
    @field: ValidEnum(enumClass = Gender::class, message = "MAN 이나 WOMAN 중 하나를 선택해 주세요.")
    @JsonProperty("gender")
    private val _gender: String?,

    @field: NotBlank
    @field: Email
    @JsonProperty("email")
    private val _email: String?
) {
    val loginId: String
        get() = _loginId!! // loginId 를 getter 로 호출시, _loginId 를 연결해준다. 그리고 _loginId는 null을 허용하는데, loginId는 null이 허용되지 않으므로 !! 를 붙여주었다. ( 어차피 null 체크는 Validator 에서 하니 상관없다. )
    val password: String
        get() = _password!!
    val name: String
        get() = _name!!
    val birthDate: LocalDate
        get() = _birthDate!!.toLocalDate()
    val gender: Gender
        get() = Gender.valueOf(_gender!!)
    val email: String
        get() = _email!!

    // String 타입 뒤에 toLocalDate() 로 명시하게 되면 LocalDate 로 반환하도록.
    private fun String.toLocalDate(): LocalDate =
        LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd")) // this: String 객체

    // entity 로 변환해서 반환하는 함수
    fun toEntity(): Member =
        Member(id, loginId, password, name, birthDate, gender, email)
}

/*
// 참고)
// valiation 을 추가하기 위해서는 dto에 데이터가 담겨있어야 함.
// 그래서 각 필드를 null 허용되도록 만들어주고, Gender 나 LocalDate 도 String? 으로 해주어야 함.
// 그런 다음에 유효성 체크 후, custom getter 로 Gender 나 LocalDate 로 내보내도록 한다.
// 변경 전)
data class MemberDtoRequest(
    val id: Long?,
    val loginId: String,
    val password: String,
    val name: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val email: String
)

// 변경 후)
data class MemberDtoRequest(
    val id: Long?,
    private val _loginId: String?,
    private val _password: String?,
    private val _name: String?,
    private val _birthDate: String?,
    private val _gender: String?,
    private val _email: String?
) {
    val loginId: String
        get() = _loginId!! // loginId 를 getter 로 호출시, _loginId 를 연결해준다. 그리고 _loginId는 null을 허용하는데, loginId는 null이 허용되지 않으므로 !! 를 붙여주었다. ( 어차피 null 체크는 Validator 에서 하니 상관없다. )
    val password: String
        get() = _password!!
    val name: String
        get() = _name!!
    val birthDate: LocalDate
        get() = _birthDate!!.toLocalDate()
    val gender: Gender
        get() = Gender.valueOf(_gender!!)
    val email: String
        get() = _email!!

    private fun String.toLocalDate(): LocalDate =
        LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}
*/