package com.example.shoppingmobile.service

import com.example.shoppingmobile.domain.category.Category
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface CategoryService {
    @GET("categories")
    fun get(@Query("catalogId") catalogId: Int?): Call<List<Category>>

    @GET("categories/{id}")
    fun findById(@Path("id") id: Int): Call<Category>

    @POST("categories")
    fun create(@Body request: Category): Call<Category>

    @PUT("categories/{id}")
    fun update(@Path("id") id: Int, @Body request: Category): Call<ResponseBody>

    @DELETE("categories/{id}")
    fun delete(@Path("id") id: Int): Call<ResponseBody>
}