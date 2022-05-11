package com.mh.mbook.vo

data class User(
    val fullname: String,
    val username: String,
    val email: String,
    val token: String,
    val role: Role,
)