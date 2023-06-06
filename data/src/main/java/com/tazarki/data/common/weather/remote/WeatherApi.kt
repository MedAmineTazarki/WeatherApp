package com.tazarki.data.common.weather.remote

import com.tazarki.domain.weather.dto.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q") city: String,
        @Query("appid") appid: String
    ) : Response<WeatherResponse>
}