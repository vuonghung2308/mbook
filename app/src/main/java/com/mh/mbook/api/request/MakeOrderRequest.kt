package com.mh.mbook.api.request

data class MakeOrderRequest(
    val name: String,
    val phone: String,
    val address: String
)