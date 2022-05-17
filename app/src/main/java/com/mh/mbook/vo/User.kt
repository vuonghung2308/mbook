package com.mh.mbook.vo

data class User(
    val id: Long,
    val fullname: String,
    val username: String,
    val email: String,
    val token: String,
    val role: Role,
)