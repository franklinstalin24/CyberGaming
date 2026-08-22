package com.example.cybergaming

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabla_juegos")
data class Juego(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val anio: Int,
    val desarrollador: String,
    val genero: String,
    val calificacion: Double,
    val urlImagen: String,
    val resenhaPersonal: String,
    val usuarioPropietario: String = "Gamer" // Valor por defecto para evitar errores si se omite en alguna llamada
)