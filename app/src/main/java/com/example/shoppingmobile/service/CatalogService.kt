package com.example.shoppingmobile.service

import com.example.shoppingmobile.domain.Catalog
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CatalogService {
    @GET("catalogs")
    suspend fun getCatalogs(): List<Catalog>

    @POST("catalogs")
    suspend fun create(@Body request: Catalog): Catalog
}