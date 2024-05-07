package com.nulp.labn3.db

import android.content.Context
import androidx.room.Room

class DatabaseManager private constructor(context: Context) {
    private val db: Hospital = Room.databaseBuilder(
        context.applicationContext,
        Hospital::class.java,
        "hospital-database"
    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

    companion object {
        @Volatile
        private var instance: DatabaseManager? = null

        fun getInstance(context: Context): DatabaseManager {
            return instance ?: synchronized(this) {
                instance ?: DatabaseManager(context).also { instance = it }
            }
        }
    }

    fun getDatabase(): Hospital {
        return db
    }
}
