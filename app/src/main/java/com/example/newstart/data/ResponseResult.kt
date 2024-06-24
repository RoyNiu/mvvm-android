package com.example.newstart.data

import com.example.newstart.data.error.BaseError

/**
 * Created by Roy
 */
sealed class ResponseResult<out T>(
    val data: T? = null,
    val code: Int? = null
) {
    class Success<out T>(data: T) : ResponseResult<T>(data)
    class Loading<T>(data: T? = null) : ResponseResult<T>(data)
    class Error(val errorCode: Int, val error: BaseError) : ResponseResult<Nothing>() {
        constructor(errorCode: Int) : this(errorCode, BaseError(errorCode,""))
    }

    override fun toString(): String {
        return when (this) {
            is Success<T> -> "Success[data= $data]"
            is Error -> "Error(errorCode=$errorCode, description='${error.content}')"
            is Loading<T> -> "Loading"
        }
    }
}


