package com.mh.mbook.api.response

data class CategoryResponse(
    val id: String,
    val name: String,
    val icon: String
) {
    val iconUrl: String
        get() = "http://192.168.1.2:8080/api/file/$icon"
}