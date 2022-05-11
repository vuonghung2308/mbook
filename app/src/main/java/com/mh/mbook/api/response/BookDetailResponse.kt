package com.mh.mbook.api.response

import java.text.NumberFormat
import java.util.*

data class BookDetailResponse(
    val id: Long,
    val name: String,
    val description: String,
    val numberOfPage: Int,
    val publisher: String,
    val image: String,
    val date: String,
    val star: Float,
    val price: Long,
    val author: String,
    val category: CategoryResponse,
    val ratings: List<RatingResponse>
) {
    companion object {
        private val locale = Locale("vi", "VN")
        private val currency = NumberFormat.getCurrencyInstance(locale)
    }

    val imageUrl: String
        get() = "http://192.168.1.2:8080/api/file/${image}"

    val displayPrice: String
        get() = currency.format(price)
}