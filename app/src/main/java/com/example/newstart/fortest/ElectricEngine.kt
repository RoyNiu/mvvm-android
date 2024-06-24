package com.example.newstart.fortest

import javax.inject.Inject

class ElectricEngine @Inject constructor(): Engine{
    override fun onStart() {
        printD("ElectricEngine start")
    }

    override fun onStop() {
        printD("ElectricEngine stop")
    }
}