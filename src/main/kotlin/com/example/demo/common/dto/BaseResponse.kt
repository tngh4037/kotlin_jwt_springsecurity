package com.example.demo.common.dto

import com.example.demo.common.status.ResultCode

// 클라이언트 요청에 대한 응답 포맷을 일원화할 객체
data class BaseResponse<T>(
    val resultCoe: String = ResultCode.SUCCESS.name,
    val data: T? = null, // 조회나 기타 처리시 결과를 반환해줄 값
    val message: String = ResultCode.SUCCESS.msg
)
