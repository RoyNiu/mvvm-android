package com.example.newstart

import android.app.Application
import com.example.newstart.utils.NetWorkUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NetWorkUtils.initialize(this)
    }


}