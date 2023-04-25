package com.example.shoppingmobile.domain.offer

import com.example.shoppingmobile.domain.category.Category
import com.example.shoppingmobile.domain.coupon.Coupon
import com.google.gson.annotations.SerializedName

data class Offer(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("coupon") val coupon: Coupon,
    @SerializedName("categories") val categories: List<Category>,
    @SerializedName("active") val active: Boolean
)
