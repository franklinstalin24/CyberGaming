package com.example.cybergaming

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "juegos")
data class Juego (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val anio: Int,
    val desarrollador: String,
    val genero: String,
    val calificacion: Double,
    val urlImagen: String //dejar pendiente para cuando se agregue la imagen
)
