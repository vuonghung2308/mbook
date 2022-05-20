package com.mh.mbook.api.request

data class SignInRequest(
    val username: String,
    val password: String,
    val token: String
)