package com.example.cybergaming

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Juego::class], version = 2, exportSchema = false)
abstract class JuegoDatabase : RoomDatabase() {
    abstract fun juegoDao(): JuegoDao

    companion object {
        @Volatile
        private var INSTANCE: JuegoDatabase? = null

        fun obtenerBaseDatos(context: Context): JuegoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JuegoDatabase::class.java,
                    "cybergaming_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}