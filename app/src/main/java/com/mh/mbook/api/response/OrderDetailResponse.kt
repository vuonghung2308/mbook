package com.mh.mbook.api.response

import java.text.NumberFormat
import java.util.*

data class OrderDetailResponse(
    val id: String,
    val code: String,
    val totalPrice: Long,
    val totalQuantity: Int,
    val createdTime: String,
    val status: Status,
    val items: List<ItemResponse>,
    val shipment: ShipmentResponse
) {
    companion object {
        private val locale = Locale("vi", "VN")
        private val currency = NumberFormat.getCurrencyInstance(locale)
    }

    val totalPriceF: String
        get() = currency.format(totalPrice)

    enum class Status(val value: String) {
        INITIATED("INITIATED"),
        CONFIRMED("CONFIRMED"),
        SHIPPED("SHIPPED")
    }
}
