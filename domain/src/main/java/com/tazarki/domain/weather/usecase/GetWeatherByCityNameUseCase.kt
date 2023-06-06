package com.tazarki.domain.weather.usecase

import com.tazarki.domain.common.BaseResult
import com.tazarki.domain.weather.dto.WeatherResponse
import com.tazarki.domain.weather.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherByCityNameUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    // Get weather by city use case
    suspend operator fun invoke(city: String)
    : Flow<BaseResult<WeatherResponse, String>> {

        return weatherRepository.getWeatherByCityName(city)

    }
}