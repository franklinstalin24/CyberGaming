package com.example.cybergaming

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JuegoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJuego(juego: Juego)

    @Query("SELECT * FROM juegos")
    fun obtenerjuegos(): Flow<List<Juego>>

    @Query("SELECT * FROM juegos WHERE id = :idBuscado")
    suspend fun obtenerJuegoPorId(idBuscado: Int): Juego?

    @Query ("DELETE FROM juegos")
    suspend fun eliminarTodosLosJuegos()

}