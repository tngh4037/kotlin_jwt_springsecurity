package com.example.demo.common.authority

data class TokenInfo (
    val grantType: String, // JWT 권한 인증 타입 ( Bearer )
    val accessToken: String, // 실제 검증할 토큰
)

// 참고) grantType과 accessToken 외에도 일반적으로 토큰을 쓸 때는 refreshToken 까지 사용하지만, 해당 강의에서는 refreshToken 까지는 사용하지 않았다.