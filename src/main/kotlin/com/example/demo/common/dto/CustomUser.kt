package com.example.demo.common.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

// CustomUserDetails 역할
class CustomUser(
    val userId: Long,
    userName: String,
    password: String,
    authorities: Collection<GrantedAuthority>
):User(userName, password, authorities) {
}