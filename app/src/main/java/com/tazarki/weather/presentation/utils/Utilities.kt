package com.tazarki.weather.presentation.utils

object Utilities {
    // Convert Kelvin to Celsius
    fun kelvinToCelsius(k: Double) : Double {
        return k - 273.15
    }
}