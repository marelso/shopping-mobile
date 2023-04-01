package com.example.shoppingmobile.service

import com.example.shoppingmobile.domain.Catalog
import retrofit2.Call
import retrofit2.http.*

interface CatalogService {
    @GET("catalogs")
    suspend fun getCatalogs(): List<Catalog>

    @POST("catalogs")
    suspend fun create(@Body request: Catalog): Catalog

    @DELETE("catalogs/{id}")
    suspend fun delete(@Path("id") id: Int): Call<Unit>
}