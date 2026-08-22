package com.example.cybergaming

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamApi {
    // Usamos Freetome / API pública de juegos o endpoint abierto de juegos (FreeToGame API es excelente para esto)
    // O una API pública de juegos sin llave: https://www.freetogame.com/api/games
    @GET("games")
    suspend fun obtenerJuegosDestacados(): List<FreeToGameModel>
}

// Modelo específico para la API ultra estable de videojuegos gratuitos (FreeToGame)
data class FreeToGameModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("platform") val platform: String
)

object RetrofitClient {
    // API 100% gratuita y pública de videojuegos de PC / Steam
    private const val BASE_URL = "https://www.freetogame.com/api/"

    val apiService: SteamApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SteamApi::class.java)
    }
}
