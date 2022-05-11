package com.mh.mbook.api.request

data class AddItemRequest(
    val bookId: Long,
    val quantity: Int
)