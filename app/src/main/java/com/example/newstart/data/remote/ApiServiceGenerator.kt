package com.example.newstart.data.remote

import com.example.newstart.BASE_URL
import com.example.newstart.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

const val timeoutRead = 30 // seconds
const val timeoutConnect = 30

private const val contentType = "Content-Type"
private const val contentTypeValue = "application/json"

/**
 * Created by Roy
 */
@Singleton
class ApiServiceGenerator @Inject constructor() {

    private val retrofit: Retrofit
    private val logger: HttpLoggingInterceptor
        get() {
            val loggerInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
            return loggerInterceptor
        }

    private var headerInterceptor = Interceptor { chain ->
        val original = chain.request()

        val request = original.newBuilder()
            .header(contentType, contentTypeValue)
            .method(original.method, original.body)
            .build()

        chain.proceed(request)
    }


    init {
        val okhttpBuilder = OkHttpClient.Builder()
        okhttpBuilder.addInterceptor(headerInterceptor)
        okhttpBuilder.addInterceptor(logger)
        okhttpBuilder.readTimeout(timeoutRead.toLong(), TimeUnit.SECONDS)
        okhttpBuilder.connectTimeout(timeoutConnect.toLong(), TimeUnit.SECONDS)
        val client = okhttpBuilder.build()
        retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun <T> createService(classService: Class<T>): T {
        return retrofit.create<T>(classService)
    }


}