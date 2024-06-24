package com.example.newstart.fortest.net

import com.example.newstart.BASE_URL
import com.example.newstart.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val timeoutRead = 30 //inSecond
private const val timeoutConnect = 30 //inSecond


@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    private val okhttpBuilder = OkHttpClient.Builder()
    private val logger: HttpLoggingInterceptor
        get() {
            val loggerInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggerInterceptor.apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            }
            return loggerInterceptor
        }

    @Singleton
    @Provides
    fun provideHttpclient(): OkHttpClient {
        okhttpBuilder.connectTimeout(timeoutConnect.toLong(), TimeUnit.SECONDS)
        okhttpBuilder.readTimeout(timeoutRead.toLong(), TimeUnit.SECONDS)
        okhttpBuilder.addInterceptor(logger)
        val client = okhttpBuilder.build()
        return client
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        return retrofit
    }

}