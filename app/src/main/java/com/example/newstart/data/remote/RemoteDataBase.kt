package com.example.newstart.data.remote

import com.example.newstart.domain.ResponseResult
import com.example.newstart.domain.DataError
import com.example.newstart.utils.NetWorkUtils
import retrofit2.Response

/**
 * Created by Roy
 */
abstract class RemoteDataBase<Service>(serviceGenerator: ApiServiceGenerator) {

    private val service: Service

    init {
        service = serviceGenerator.createService(getService())
    }


    abstract fun getService(): Class<Service>

    suspend fun <R> apiCall(apiCall: suspend Service.() -> Response<R>): ResponseResult<R, DataError> {
        if (!NetWorkUtils.isConnected()) {
            return ResponseResult.Error(DataError.Network.NO_INTERNET)

        }
        try {
            val response = service.apiCall()
            val responseCode = response.code()
            if (response.isSuccessful) {
                return ResponseResult.Success(response.body()!!)
            } else {
                //just handle basic net error, some specific error are handle over to business and keep the error info
                return when (responseCode) {
                    in DataError.Network.entries.map { it.code } -> {
                        ResponseResult.Error(DataError.Network.entries.first() {
                            it.code == responseCode
                        })
                    }
                    else -> ResponseResult.Error(
                        DataError.CustomError(responseCode, response.message())
                    )
                }
            }
        } catch (e: Exception) {
            return ResponseResult.Error(DataError.Network.NO_INTERNET)
        }
    }
}




