package com.example.cybergaming

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Juego::class], version = 1, exportSchema = false)
abstract class JuegoDatabase : RoomDatabase() {
    abstract fun juegoDao(): JuegoDao

    companion object {

        @Volatile
        private var INSTANCE: JuegoDatabase? = null

        fun getDatabase(context: Context): JuegoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    JuegoDatabase::class.java,
                    "cybergaming_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}