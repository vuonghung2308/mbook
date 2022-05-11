package com.mh.mbook.api.response

data class RatingResponse(
    val id: Long,
    val star: Int,
    val command: String,
    val user: UserResponse
)