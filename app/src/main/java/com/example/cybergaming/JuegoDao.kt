package com.example.cybergaming

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JuegoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarJuego(juego: Juego)

    @Delete
    suspend fun eliminarJuego(juego: Juego)

    @Query("SELECT * FROM tabla_juegos WHERE usuarioPropietario = :usuario")
    fun obtenerJuegosPorUsuario(usuario: String): Flow<List<Juego>>

    @Query("SELECT * FROM tabla_juegos WHERE id = :idBuscado")
    suspend fun obtenerJuegoPorId(idBuscado: Int): Juego?

    @Query("SELECT * FROM tabla_juegos WHERE nombre = :nombreJuego AND usuarioPropietario = :usuario LIMIT 1")
    suspend fun obtenerJuegoPorNombreYUsuario(nombreJuego: String, usuario: String): Juego?
}