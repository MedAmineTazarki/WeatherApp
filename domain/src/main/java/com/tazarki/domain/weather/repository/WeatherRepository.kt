package com.tazarki.domain.weather.repository

import com.tazarki.domain.common.BaseResult
import com.tazarki.domain.weather.dto.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    // Get weather by city
    suspend fun getWeatherByCityName(city: String)
            : Flow<BaseResult<WeatherResponse, String>>

}