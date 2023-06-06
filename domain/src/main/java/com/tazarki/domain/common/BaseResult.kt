package com.tazarki.domain.common

// Define the type of results we could receive from the api call (whether it's successful, loading, error ...)
sealed class BaseResult<out T : Any, out U : Any> {
    data class Success<T : Any>(val data: T) : BaseResult<T, Nothing>()
    data class ErrorUnknown<U : Any>(val rawResponse: U? = null) : BaseResult<Nothing, U>()
    data class ErrorWrongEntry<U : Any>(val rawResponse: U? = null) : BaseResult<Nothing, U>()

    object Loading : BaseResult<Nothing, Nothing>()
    object Init : BaseResult<Nothing, Nothing>()
    object NoContent : BaseResult<Nothing, Nothing>()
}