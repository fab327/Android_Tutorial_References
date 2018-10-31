package com.example.android.sunshine.data.database

import android.arch.persistence.room.*
import android.content.Context

/**
 * The database object.
 * The elements inside the companion object act as static components.
 */
@Database(entities = [(WeatherEntry::class)], version = 1)
@TypeConverters(DateConverter::class)
abstract class SunshineDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        private val DATABSE_NAME = "weather"

        private val LOCK = Any()
        private var sInstance: SunshineDatabase? = null

        fun getInstance(context: Context): SunshineDatabase? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = Room.databaseBuilder(context.applicationContext, SunshineDatabase::class.java, DATABSE_NAME).build()
                }
            }
            return sInstance
        }
    }
}