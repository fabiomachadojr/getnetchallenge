package com.challenge.petnet.data.remote

import com.challenge.petnet.data.model.DetailItemResponse
import com.challenge.petnet.data.model.ItemResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PetApiService {

    @GET("/")
    suspend fun getItems(): List<ItemResponse>

    @GET("item/{id}")
    suspend fun getItemDetail(@Path("id") id: Int): DetailItemResponse
}