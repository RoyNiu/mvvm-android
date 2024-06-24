package com.example.newstart.data.error

import okhttp3.internal.http2.ErrorCode

/**
 * Created by Roy
 */
class BaseError(val code: Int, val content: String) {
    constructor(exception: Exception) : this(
        code = DEFAULT_ERROR,
        content = exception.message ?: "")
}
const val DEFAULT_ERROR = -1
const val NETWORK_ERROR = -2
const val NO_INTERNET_CONNECTION = -3

const val PASS_WORD_ERROR = -101
const val USER_NAME_ERROR = -102
const val CHECK_YOUR_FIELDS = -103
const val SEARCH_ERROR = -104