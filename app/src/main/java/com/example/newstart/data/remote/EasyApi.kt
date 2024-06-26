package com.example.newstart.data.remote

import android.util.Log
import com.example.newstart.domain.DataError
import com.example.newstart.domain.ResponseResult
import com.example.newstart.utils.NetWorkUtils
import retrofit2.Response

/**
 * Created by Roy
 */
object EasyApi {

    private val serviceGenerator: ApiServiceGenerator = ApiServiceGenerator()
    private val serviceMap: MutableMap<Class<*>, Any> = mutableMapOf()

    @Synchronized
    fun <T : Any> getService(serviceClass: Class<T>): T {
        return serviceMap.getOrPut(serviceClass) {
            serviceGenerator.createService(serviceClass)
        } as T
    }

    suspend fun <Service : Any, R> apiCall(
        serviceClass: Class<Service>,
        apiCall: suspend Service.() -> Response<R>
    ): ResponseResult<R, DataError> {
        val service = getService(serviceClass)
        if (!NetWorkUtils.isConnected()) {
            return ResponseResult.Error(DataError.Network.NO_INTERNET)
        }
        return try {
            val response = service.apiCall()
            val responseCode = response.code()
            if (response.isSuccessful) {
                ResponseResult.Success(response.body()!!)
            } else {
                //just handle basic net error, some specific error are handle over to business and keep the error info
                return when (responseCode) {
                    in DataError.Network.entries.map { it.code } -> {
                        ResponseResult.Error(DataError.Network.entries.first() {
                            it.code == responseCode
                        })
                    }

                    else -> ResponseResult.Error(
                        DataError.CustomError(
                            responseCode,
                            response.message()
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("EasyApi", "API call failed", e)
            ResponseResult.Error(DataError.Network.UNKNOWN)
        }
    }
}
