package com.mh.mbook.api.response

data class UserResponse(
    val id: Long,
    val username: String,
    val fullname: String,
    val email: String
)