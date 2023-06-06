package com.tazarki.weather.presentation.fragments.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tazarki.domain.common.BaseResult
import com.tazarki.domain.weather.dto.WeatherResponse
import com.tazarki.domain.weather.usecase.GetWeatherByCityNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherByCityNameUseCase: GetWeatherByCityNameUseCase
    ) : ViewModel() {

    private val _progress = MutableStateFlow(0)
    val progress = _progress.asStateFlow()

    private val _weatherData = MutableStateFlow<BaseResult<WeatherResponse,
            String>>(BaseResult.Init)
    val weatherData : StateFlow<BaseResult<WeatherResponse,
            String>>
        get() = _weatherData


    // When updating the progress we need to start by resetting it
    fun updateProgress(){
        // We reset the progress in case we restart the process so we need to start from 0 again
        resetProgress()
        // Inside this Coroutine we iterate 60 times and delay each iteration with 1 seconds which results in counter from 0 to 60 seconds
        viewModelScope.launch {
            repeat(60){
                delay(1000L)
                _progress.emit(it+1)
            }
        }
    }

    // Reset the progress to 0
    private fun resetProgress(){
        _progress.value = 0
    }

    // We call the api with the corresponding city name and get the result
    fun getWeatherByCityName(city: String){
        // We launch a Coroutine to call the weather useCase and get the result
        viewModelScope.launch {
            weatherByCityNameUseCase(city)
                .onStart {
                    // We initiate the state to the Loading when we start
                    _weatherData.value = BaseResult.Loading
                }
                .collect{
                    // After collecting the result, we check if it was successful or not to emit the correct value
                    when(it) {
                        is BaseResult.Success -> {
                            // If we successfully got the result
                            _weatherData.value = it
                        }
                        is BaseResult.ErrorUnknown -> {
                            // If there was some kind of an error
                            _weatherData.value = it
                        }
                        else -> {
                            // In the other cases that should be handled independently
                        }
                    }
                }
        }
    }

}