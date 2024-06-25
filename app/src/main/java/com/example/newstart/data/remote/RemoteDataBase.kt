package com.example.newstart.data.remote

import com.example.newstart.domain.ResponseResult
import com.example.newstart.data.error.BaseError
import com.example.newstart.data.error.DEFAULT_ERROR
import com.example.newstart.data.error.NO_INTERNET_CONNECTION
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

    suspend fun <R> apiCall(apiCall: suspend Service.() -> Response<R>): ResponseResult<R, DataError.Network> {
        if (!NetWorkUtils.isConnected()) {
            return ResponseResult.Error(DataError.Network.NO_INTERNET)

        }
        try {
            val response = service.apiCall()
            val responseCode = response.code()
            if (response.isSuccessful) {
                return ResponseResult.Success(response.body()!!)
            } else {
                //FIXME
                return ResponseResult.Error(DataError.Network.SERVER_ERROR)
            }
        } catch (e: Exception) {
            return ResponseResult.Error(DataError.Network.NO_INTERNET)
        }
    }
}




