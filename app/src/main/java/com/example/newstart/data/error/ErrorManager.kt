package com.example.newstart.data.error

import android.content.Context
import com.example.newstart.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Roy
 */
class ErrorManager @Inject constructor(@ApplicationContext val context: Context) : ErrorCase {

    override fun getError(errorCode: Int): BaseError {
        return BaseError(code = errorCode, content = errorMap.getValue(errorCode))
    }

    private val errorMap: Map<Int, String>
        get() = mapOf(
            Pair(NO_INTERNET_CONNECTION, getString(R.string.no_internet)),
            Pair(NETWORK_ERROR, getString(R.string.network_error)),
            Pair(PASS_WORD_ERROR, getString(R.string.invalid_password)),
            Pair(USER_NAME_ERROR, getString(R.string.invalid_username)),
            Pair(CHECK_YOUR_FIELDS, getString(R.string.invalid_username_and_password)),
            Pair(SEARCH_ERROR, getString(R.string.search_error))
        ).withDefault { getString(R.string.network_error) }

    private fun getString(res: Int): String {
        return context.getString(res)
    }
}