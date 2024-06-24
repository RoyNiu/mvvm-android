package com.example.newstart.data.error

/**
 * Created by Roy
 */
interface ErrorCase {

    fun getError(errorCode: Int): BaseError
}