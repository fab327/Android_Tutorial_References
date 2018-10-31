package com.justfabcodes.android.sunshine.ui.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.justfabcodes.android.sunshine.data.SunshineRepository
import com.justfabcodes.android.sunshine.data.database.WeatherEntry
import com.justfabcodes.android.sunshine.utilities.SunshineDateUtils

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