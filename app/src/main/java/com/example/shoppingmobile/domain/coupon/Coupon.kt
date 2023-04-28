package com.example.shoppingmobile.domain.coupon

import com.google.gson.annotations.SerializedName

data class Coupon(
    @SerializedName("id") val id: Int,
    @SerializedName("code") val code: String
)
