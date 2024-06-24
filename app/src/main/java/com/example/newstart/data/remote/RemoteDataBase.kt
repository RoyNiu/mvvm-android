package com.example.newstart.data.remote

import com.example.newstart.data.ResponseResult
import com.example.newstart.data.error.BaseError
import com.example.newstart.data.error.DEFAULT_ERROR
import com.example.newstart.data.error.NO_INTERNET_CONNECTION
import com.example.newstart.utils.NetWorkUtils
import retrofit2.Response

/**
 * Created by Roy
 */
abstract class RemoteDataBase<Service> (serviceGenerator: ApiServiceGenerator) {

    private val service: Service

    init {
        service = serviceGenerator.createService(getService())
    }


    abstract fun getService(): Class<Service>

    suspend fun <R> apiCall(apiCall: suspend Service.() -> Response<R>): ResponseResult<R> {
        if (!NetWorkUtils.isConnected()) {
            return ResponseResult.Error(NO_INTERNET_CONNECTION)

        }
        try {
            val response = service.apiCall()
            val responseCode = response.code()
            if (response.isSuccessful) {
                return ResponseResult.Success(response.body()!!)
            } else {
                return ResponseResult.Error(
                    responseCode,
                    BaseError(responseCode, response.message())
                )
            }
        } catch (e: Exception) {
            return ResponseResult.Error(DEFAULT_ERROR, BaseError(e))
        }
    }
}

    //    protected suspend fun <R> apiCall(apiCall: suspend Service.() -> Response<R>): ResponseResult<R> {
//        return withContext(Dispatchers.IO) {
//            if (!networkUtils.isConnected()) {
//                return@withContext ResponseResult.Error(NO_INTERNET_CONNECTION)
//
//            }
//            try {
//                val response = service.apiCall()
//                val responseCode = response.code()
//                if (response.isSuccessful) {
//                    ResponseResult.Success(response.body()!!)
//                } else {
//                    ResponseResult.Error(responseCode, BaseError(responseCode, responseCode.))
//                }
//            } catch (e: Exception) {
//                ResponseResult.Error(DEFAULT_ERROR, BaseError(e))
//            }
//        }
//    }



