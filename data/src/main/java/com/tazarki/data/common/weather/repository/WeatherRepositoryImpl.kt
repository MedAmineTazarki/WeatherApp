package com.tazarki.data.common.weather.repository

import com.tazarki.data.common.weather.remote.WeatherApi
import com.tazarki.domain.BuildConfig
import com.tazarki.domain.common.BaseResult
import com.tazarki.domain.weather.dto.WeatherResponse
import com.tazarki.domain.weather.repository.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ExperimentalCoroutinesApi
class WeatherRepositoryImpl @Inject constructor(private val weatherApi: WeatherApi) :
    WeatherRepository {


    // Override the get weather by city from the repository to make the api call and emit the result
    override suspend fun getWeatherByCityName(
        city: String
    ): Flow<BaseResult<WeatherResponse, String>> {
        return flow {
            try {
                val response = weatherApi.getWeatherByCityName(
                    city,
                    BuildConfig.API_KEY
                )

                if (response.isSuccessful){
                    val body = response.body()!!
                    emit(BaseResult.Success(body))
                }else{
                    emit(BaseResult.ErrorUnknown())
                }

            }catch (e: Exception){
                emit(BaseResult.ErrorUnknown())
            }
        }
    }
}