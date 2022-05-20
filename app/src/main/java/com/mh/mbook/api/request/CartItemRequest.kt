package com.mh.mbook.api.request

data class CartItemRequest(
    val bookId: Long,
    val quantity: Int
)