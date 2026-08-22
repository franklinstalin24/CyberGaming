package com.example.cybergaming

import android.util.Log
import kotlinx.coroutines.flow.Flow

class JuegoRepository(
    private val juegoDao: JuegoDao,
    private val steamApi: SteamApi
) {
    fun obtenerJuegosLocales(usuario: String): Flow<List<Juego>> {
        return juegoDao.obtenerJuegosPorUsuario(usuario)
    }

    suspend fun insertarJuegoLocal(juego: Juego): Boolean {
        val existente = juegoDao.obtenerJuegoPorNombreYUsuario(juego.nombre, juego.usuarioPropietario)
        if (existente == null) {
            juegoDao.insertarJuego(juego)
            return true
        }
        return false
    }

    suspend fun eliminarJuegoLocal(juego: Juego) {
        juegoDao.eliminarJuego(juego)
    }

    suspend fun obtenerJuegoLocalPorId(id: Int): Juego? {
        return juegoDao.obtenerJuegoPorId(id)
    }

    suspend fun obtenerJuegosDestacadosDeRed(): Result<List<SteamGame>> {
        return try {
            val listaBruta = steamApi.obtenerJuegosDestacados()
            val listaConvertida = listaBruta.map { juegoApi ->
                SteamGame(
                    id = juegoApi.id,
                    name = juegoApi.title,
                    largeCapsuleImage = juegoApi.thumbnail,
                    rating = 4.5
                )
            }
            Result.success(listaConvertida)
        } catch (e: Exception) {
            Log.e("CyberGaming", "Error de red: ${e.message}")
            Result.failure(e)
        }
    }
}