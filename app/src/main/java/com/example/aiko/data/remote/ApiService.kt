package com.example.aiko.data.remote

import com.example.aiko.data.model.Position
import retrofit2.http.GET

interface ApiService {
    @GET("Posicao")
    suspend fun getPosition(): Position
}