package com.example.android.sunshine.ui.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.android.sunshine.data.SunshineRepository
import com.example.android.sunshine.data.database.WeatherEntry
import com.example.android.sunshine.utilities.SunshineDateUtils
import com.example.android.sunshine.utilities.SunshineWeatherUtils
import java.util.*

data class MainActivityViewModel(
        val repository: SunshineRepository
): ViewModel() {

    var weatherEntries: LiveData<List<WeatherEntry>>? = null

    init {
        weatherEntries = repository.getWeatherAfterDate(SunshineDateUtils.getNormalizedUtcDateForToday())
    }

    fun getWeather(): LiveData<List<WeatherEntry>>? {
        return weatherEntries
    }

}