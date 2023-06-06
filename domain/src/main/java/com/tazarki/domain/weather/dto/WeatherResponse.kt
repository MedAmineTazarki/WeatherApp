package com.tazarki.domain.weather.dto

import com.tazarki.domain.BuildConfig

// The data we receive from the api call
data class WeatherResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

// The data we want to show
data class WeatherInfo(
    val city: String,
    val temperature: Double,
    val cloudIcon: String
)

// Extension function to map the result from api and return the data we want to show
fun WeatherResponse.toWeatherInfo() = WeatherInfo(
    city = name,
    temperature = main.temp,
    // Concat the base url of the icon + icon name + extension which based on the doc it's always png
    cloudIcon = BuildConfig.ICON_BASE_URL+weather.first().icon+".png"
)