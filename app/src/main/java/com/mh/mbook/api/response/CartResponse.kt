package com.mh.mbook.api.response

import java.text.NumberFormat
import java.util.*

data class CartResponse(
    val id: Long,
    val totalPrice: Long,
    val totalQuantity: Long,
    val items: List<ItemResponse>
) {
    companion object {
        private val locale = Locale("vi", "VN")
        private val currency = NumberFormat.getCurrencyInstance(locale)
    }

    val totalPriceF: String
        get() = currency.format(totalPrice)
}