package com.example.shoppingmobile.service

import com.example.shoppingmobile.domain.Catalog
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.*

interface CatalogService {
    @GET("catalogs")
    suspend fun getCatalogs(): List<Catalog>

    @POST("catalogs")
    suspend fun create(@Body request: Catalog): Catalog

    @PUT("catalogs/{id}")
    suspend fun update(@Path("id") id: Int, @Body request: Catalog): Response<ResponseBody>

    @DELETE("catalogs/{id}")
    suspend fun delete(@Path("id") id: Int): Response<ResponseBody>
}