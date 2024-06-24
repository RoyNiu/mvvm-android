package com.example.newstart.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import javax.inject.Inject

/**
 * Created by Roy
 *
 * Its lifecycle is same with application
 */
@SuppressLint("StaticFieldLeak")
object NetWorkUtils : NetworkConnectivity {

    private lateinit var context: Context

    fun initialize(context: Context) {
        this.context = context.applicationContext
    }

    override fun getNetworkInfo(): NetworkInfo? {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo
    }

    override fun isConnected(): Boolean {
        val info = getNetworkInfo()
        return info != null && info.isConnected
    }
}

interface NetworkConnectivity {
    fun getNetworkInfo(): NetworkInfo?
    fun isConnected(): Boolean
}