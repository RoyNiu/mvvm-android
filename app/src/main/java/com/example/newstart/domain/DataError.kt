package com.example.newstart.domain

/**
 * Created by Roy
 */

sealed interface DataError: Error {
   //Common Network Error
    enum class Network(val code: Int): DataError {
        REQUEST_TIMEOUT(408),
        NOT_FOUND(404),
        NO_INTERNET(-999),
        PAYLOAD_TOO_LARGE(411),
        SERVER_ERROR(501),
        UNKNOWN(-1)
    }
    enum class Local: DataError {
        DISK_FULL,
        UNKNOWN
    }

    data class CustomError(val errorCode: Int, val message: String) : DataError
}



