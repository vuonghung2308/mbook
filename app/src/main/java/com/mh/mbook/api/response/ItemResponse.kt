package com.mh.mbook.api.response

import java.text.NumberFormat
import java.util.*

data class ItemResponse(
    val id: Long,
    val name: String,
    val image: String,
    val star: Float,
    val price: Long,
    val author: String,
    val quantity: Int,
    val ratings: Int
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