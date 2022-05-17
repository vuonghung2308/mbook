package com.mh.mbook.api.response

import com.mh.mbook.di.AppModule
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
    val totalRating: Int,
    val category: CategoryResponse,
    val ratings: List<RatingResponse>
) {
    companion object {
        private val locale = Locale("vi", "VN")
        private val currency = NumberFormat.getCurrencyInstance(locale)
    }

    val imageUrl: String
        get() = "${AppModule.BASE_URL}/api/file/${image}"

    val displayPrice: String
        get() = currency.format(price)
}