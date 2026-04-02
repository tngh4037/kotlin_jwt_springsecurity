package com.example.demo.member.controller

import com.example.demo.common.dto.BaseResponse
import com.example.demo.member.dto.MemberDtoRequest
import com.example.demo.member.service.MemberService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/member")
@RestController
class MemberController (
    private val memberService: MemberService
) {

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    fun signUp(@Valid @RequestBody memberDtoRequest: MemberDtoRequest): BaseResponse<Unit> { // 참고) resultCode 와 message 만 반환하고, data 는 응답시 사용하지 않으므로 Unit 사용
        val resultMsg: String = memberService.signUp(memberDtoRequest)
        return BaseResponse(message = resultMsg)
    }
}