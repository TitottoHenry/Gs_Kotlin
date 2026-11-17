package com.example.alertaclima.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.alertaclima.model.AlertEntity

@Database(entities = [AlertEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AlertDatabase : RoomDatabase() {

    abstract fun alertDao(): AlertDao

    companion object {
        @Volatile
        private var INSTANCE: AlertDatabase? = null

        fun getInstance(context: Context): AlertDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AlertDatabase::class.java,
                    "alerta_clima.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
