package com.mh.mbook.api.response

import com.mh.mbook.di.AppModule

data class CategoryResponse(
    val id: String,
    val name: String,
    val icon: String
) {
    val iconUrl: String
        get() = "${AppModule.BASE_URL}/api/file/$icon"
}