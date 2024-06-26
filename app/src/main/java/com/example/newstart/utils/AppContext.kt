package com.example.newstart.utils

import android.content.Context
import androidx.annotation.StringRes

/**
 * Created by Roy
 */
object AppContext {
    private lateinit var appContext: Context

    fun initialize(context: Context) {
        appContext = context.applicationContext
    }

    fun getAppContext(): Context {
        return appContext
    }

    fun getString(@StringRes res: Int):String {
        return appContext.getString(res)
    }
}