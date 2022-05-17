package com.mh.mbook.api.request

data class CommentRequest(
    val bookId: Long,
    val star: Int,
    val comment: String?
)
