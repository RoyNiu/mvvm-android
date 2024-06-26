package com.example.newstart.utils

import com.example.newstart.R
import com.example.newstart.domain.DataError
import com.example.newstart.domain.ResponseResult

/**
 * Created by Roy
 */

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.the_request_timed_out
        )


        DataError.Network.NO_INTERNET -> UiText.StringResource(
            R.string.no_internet
        )

        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(
            R.string.file_too_large
        )

        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.server_error
        )

        DataError.Network.UNKNOWN -> UiText.StringResource(
            R.string.unknown_error
        )

        DataError.Local.DISK_FULL -> UiText.StringResource(
            R.string.error_disk_full
        )

        DataError.Local.UNKNOWN -> TODO()
        DataError.Network.NOT_FOUND -> TODO()
        is DataError.CustomError -> TODO()
    }
}

fun ResponseResult.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}