package com.example.appbarfanny.data.api

import com.example.appbarfanny.data.model.Bebida
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @GET("bebidas/")
    suspend fun getBebidas(): List<Bebida> // GET (Lista)

    @POST("bebidas/")
    suspend fun createBebida(@Body bebida: Bebida): Bebida // POST

    // ... (PUT, DELETE, etc.)
}