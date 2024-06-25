package com.example.newstart.domain

import com.example.newstart.data.error.BaseError

/**
 * Created by Roy
 */
typealias RootError = Error

sealed interface ResponseResult<out T, out E : RootError> {
    data class Success<out T, out E : RootError>(val data: T) : ResponseResult<T, E>
    data class Error<out T, out E : RootError>(val error: E) : ResponseResult<T, E>

    class Loading<out T, out E : RootError> : ResponseResult<T, E>
}


