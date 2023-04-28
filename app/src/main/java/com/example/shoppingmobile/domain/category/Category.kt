package com.example.shoppingmobile.domain.category

import com.google.gson.annotations.SerializedName

data class Category (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("priority") val priority: Int,
    @SerializedName("catalogId") val catalogId: Int
)
