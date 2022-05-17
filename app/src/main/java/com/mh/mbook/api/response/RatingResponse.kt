package com.mh.mbook.api.response

data class RatingResponse(
    val id: Long,
    val star: Int,
    val comment: String?,
    val user: UserResponse
)