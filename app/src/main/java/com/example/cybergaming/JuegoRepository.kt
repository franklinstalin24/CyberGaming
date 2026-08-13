package com.example.cybergaming

import android.util.Log
import kotlinx.coroutines.flow.Flow

class JuegoRepository(
    private val juegoDao: JuegoDao,
    private val steamApi: SteamApi
) {
    // --- 1. OPERACIONES LOCALES (ROOM) ---

    // Obtenemos el Flow de la base de datos local
    val juegosLocales: Flow<List<Juego>> = juegoDao.obtenerTodosLosJuegos()

    suspend fun insertarJuegoLocal(juego: Juego) {
        juegoDao.insertarJuego(juego)
    }

    suspend fun obtenerJuegoLocalPorId(id: Int): Juego? {
        return juegoDao.obtenerJuegoPorId(id)
    }

    // --- 2. OPERACIONES DE RED (RETROFIT - STEAM) ---

    suspend fun obtenerJuegosDestacadosDeRed(): List<SteamGame> {
        return try {
            val respuesta = steamApi.obtenerJuegosDestacados()
            respuesta.featuredWin ?: emptyList()
        } catch (e: Exception) {
            Log.e("CyberGaming", "Error en Repositorio de Red: ${e.message}")
            emptyList() // Devuelve lista vacía si falla la conexión para evitar caídas
        }
    }
}