package com.example.newstart.fortest

import android.app.Application
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


class Truck @Inject constructor(val application: Application) {


    @Inject
    @BindGasEngine
    lateinit var gasEngine: Engine

    @Inject
    @BindElectricEngine
    lateinit var electricEngine: Engine

    fun deliver(){
        gasEngine.onStart()
        printD("truck is delivering")
        gasEngine.onStop()

        electricEngine.onStart()
        printD("electric is delivering")
        electricEngine.onStop()
    }



}