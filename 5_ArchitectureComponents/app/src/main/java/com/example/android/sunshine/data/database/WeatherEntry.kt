/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.sunshine.data.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * tableName -> without it being set, the default name would be weatherentry
 * indices -> gives a special directive. In this case we should only store one entry per date
 *
 * We need 2 separate constructors here because the network parser doesn't care for the id property
 * and therefore that constructor is annotated as such so that Room knows which one to use for db insertion
 */
@Entity(tableName = "weather", indices = [Index(value = "date", unique = true)])
data class WeatherEntry(@PrimaryKey(autoGenerate = true) var id: Int,
                        var weatherIconId: Int,
                        var date: Date,
                        var min: Double,
                        var max: Double,
                        var humidity: Double,
                        var pressure: Double,
                        var wind: Double,
                        var degrees: Double) {

    @Ignore
    constructor(weatherIconId: Int, date: Date, min: Double, max: Double, humidity: Double, pressure: Double, wind: Double, degrees: Double)
            : this(0, weatherIconId, date, min, max, humidity, pressure, wind, degrees)
}