package com.example.cybergaming

import com.google.gson.annotations.SerializedName

// Clase principal que envuelve la respuesta de la API de Steam (ej. /api/featured/)
data class SteamDestacado(
    @SerializedName("results")
    val resultado: List<SteamGame>
)

// Datos individuales de cada juego que trae la API
data class SteamGame(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("large_capsule_image") val largeCapsuleImage: String,
    @SerializedName("final_price") val finalPrice: Int?,
    @SerializedName("discount_percent") val discount_percent: Int?
)