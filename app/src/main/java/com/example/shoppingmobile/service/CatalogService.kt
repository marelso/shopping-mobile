package com.example.shoppingmobile.service

import com.example.shoppingmobile.domain.Catalog
import retrofit2.http.GET

interface CatalogService {
    @GET("catalogs")
    suspend fun getCatalogs(): List<Catalog>
}