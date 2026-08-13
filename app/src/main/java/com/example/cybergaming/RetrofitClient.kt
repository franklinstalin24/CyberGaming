package com.example.cybergaming

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Interfaz que define los endpoints de la API
interface SteamApi {
    @GET("juegos destacados")
    suspend fun obtenerJuegosDestacados(

    ): SteamDestacado
}

// Objeto Singleton para mantener una única instancia de Retrofit en toda la app
object RetrofitClient {
    private const val BASE_URL = "https://store.steampowered.com/api/featured/"

    val apiService: SteamApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SteamApi::class.java)
    }
}