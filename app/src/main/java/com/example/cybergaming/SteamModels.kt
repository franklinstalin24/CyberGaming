package com.example.cybergaming

import com.google.gson.annotations.SerializedName

// Estructura adaptada para recibir videojuegos reales
data class RawgResponse(
    @SerializedName("results")
    val results: List<SteamGame>?
)

data class SteamGame(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("background_image") val largeCapsuleImage: String?,
    @SerializedName("rating") val rating: Double?
)