package com.mh.mbook.api.request

import com.mh.mbook.vo.Role

data class SignUpRequest(
    val username: String,
    val password: String,
    val email: String,
    val role: Role = Role.CUSTOMER
)
