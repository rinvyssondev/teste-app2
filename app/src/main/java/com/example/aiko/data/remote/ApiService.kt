package com.example.aiko.data.remote

import com.example.aiko.data.model.Position
import com.example.aiko.data.model.StopBusItem
import retrofit2.http.GET

interface ApiService {
    @GET("Posicao")
    suspend fun getPosition(): Position

    @GET("Parada/Buscar?termosBusca=")
    suspend fun getStopBus(): List<StopBusItem>
}