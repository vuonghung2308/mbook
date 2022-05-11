package com.mh.mbook.api.request

data class VerifyRequest(
    val email: String,
    val code: String
)