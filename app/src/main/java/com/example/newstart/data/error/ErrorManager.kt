package com.example.newstart.data.error

import android.content.Context
import com.example.newstart.R
import com.example.newstart.domain.DataError
import com.example.newstart.domain.Error


/**
 * Created by Roy
 */
abstract class ErrorManager(val context: Context) : ErrorCase {

    override fun getErrorMsg(e: Error): String {
        return errorMap.getValue(e)
    }

    private val errorMap: Map<Error, String> by lazy {
        val networkErrors = mapOf(
            DataError.Network.NO_INTERNET to getString(R.string.no_internet),
            DataError.Network.UNKNOWN to getString(R.string.network_error),
            DataError.Network.SERVER_ERROR to getString(R.string.server_error),
            DataError.Network.REQUEST_TIMEOUT to getString(R.string.the_request_timed_out),
            DataError.Network.NOT_FOUND to getString(R.string.network_error)
        )

        val localErrors = mapOf(
            DataError.Local.DISK_FULL to getString(R.string.disk_full),
            DataError.Local.UNKNOWN to getString(R.string.unknown_error)
        )

        featureErrorMap() + networkErrors + localErrors + mapOf<Error, String>().withDefault {
            getString(R.string.network_error)
        }
    }

     abstract fun featureErrorMap(): Map<out Error, String>

    private fun getString(res: Int): String {
        return context.getString(res)
    }
}