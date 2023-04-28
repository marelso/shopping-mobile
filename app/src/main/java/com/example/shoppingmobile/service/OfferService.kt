package com.example.shoppingmobile.service

import com.example.shoppingmobile.domain.offer.Offer
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface OfferService {
    @GET("offers")
    fun get(): Call<List<Offer>>

    @GET("offers/{id}")
    fun find(@Path("id") id: Int): Call<Offer>

    @GET("offers/category/{id}")
    fun getByCategory(@Path("id") id: Int): Call<List<Offer>>

    @POST("offers")
    fun create(@Body request: Offer): Call<Offer>

    @PUT("offers/{id}")
    fun update(@Path("id") id: Int, @Body request: Offer): Call<ResponseBody>

    @DELETE("offers/{id}")
    fun delete(@Path("id") id: Int): Call<ResponseBody>
}