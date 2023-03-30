package com.example.shoppingmobile.service

import com.example.shoppingmobile.domain.Catalog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "http://localhost:8080/catalogs"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val catalogApi = retrofit.create(ApiClient::class.java)

    suspend fun getCatalogs(): List<Catalog> {
        return withContext(Dispatchers.IO) {
            catalogApi.getCatalogs()
        }
    }

}