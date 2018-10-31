package com.justfabcodes.android.sunshine.data.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import java.util.*

/**
 * The dao (database access object) defines all the database operations related to weather objects
 */
@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(vararg weather: WeatherEntry)

    @Query("SELECT * FROM weather WHERE date = :date")
    fun getWeatherByDate(date: Date): LiveData<WeatherEntry>

    @Query("SELECT COUNT(id) FROM weather WHERE date >= :date")
    fun countAllFutureWeather(date: Date): Int

    @Query("DELETE FROM weather WHERE date < :date")
    fun deleteOldWeatherData(date: Date)

    @Query("SELECT * FROM weather WHERE date > :date")
    fun getWeatherAfterDate(date: Date): LiveData<List<WeatherEntry>>

}