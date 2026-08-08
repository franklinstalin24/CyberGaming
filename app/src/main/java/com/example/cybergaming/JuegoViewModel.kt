package com.example.cybergaming

import androidx.lifecycle.ViewModel

class JuegoViewModel : ViewModel() {

    //1. lista privada de juegos

    private val _juegos = mutableListOf<Juego>(
        Juego(1, "The Legend of Zelda: Breath of the Wild", "Aventura y acción en un mundo abierto", 2017, "Nintendo", "Aventura", 9.5, ""),
        Juego(2, "God of War", "Aventura y acción basada en la mitología nórdica", 2018, "Santa Monica Studio", "Aventura", 9.3, ""),
        Juego(3, "Red Dead Redemption 2", "Juego de acción y aventura en el Viejo Oeste", 2018, "Rockstar Games", "Acción/Aventura", 9.7, ""),
        Juego(4, "The Witcher 3: Wild Hunt", "RPG de acción en un mundo abierto lleno de monstruos y magia", 2015, "CD Projekt Red", "RPG/Aventura", 9.8, ""),
        Juego(5, "Minecraft", "Juego de construcción y supervivencia en un mundo generado por bloques", 2011, "Mojang Studios", "Sandbox/Aventura", 9.0, "")
    )

    //2. lista pública de juegos

    val juegos: List<Juego> = _juegos

    //3. función para buscar un juego

    fun obtenerJuegoPorId(id: Int): Juego? {

        //find busca el juego
        return _juegos.find { it.id == id }
    }
}