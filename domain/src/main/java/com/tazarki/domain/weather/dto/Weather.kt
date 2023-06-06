package com.tazarki.domain.weather.dto

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)