package com.example.newstart.data.remote

import android.util.Log
import com.example.newstart.data.ResponseResult
import com.example.newstart.data.error.BaseError
import com.example.newstart.data.error.DEFAULT_ERROR
import com.example.newstart.data.error.NO_INTERNET_CONNECTION
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
    ): ResponseResult<R> {
        val service = getService(serviceClass)
        if (!NetWorkUtils.isConnected()) {
            return ResponseResult.Error(NO_INTERNET_CONNECTION)
        }
        return try {
            val response = service.apiCall()
            val responseCode = response.code()
            if (response.isSuccessful) {
                ResponseResult.Success(response.body()!!)
            } else {
                ResponseResult.Error(responseCode, BaseError(responseCode, response.message()))
            }
        } catch (e: Exception) {
            Log.e("EasyApi", "API call failed", e)
            ResponseResult.Error(DEFAULT_ERROR, BaseError(e))
        }
    }
}
