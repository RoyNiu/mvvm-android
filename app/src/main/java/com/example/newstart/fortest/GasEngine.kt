package com.example.newstart.fortest

import javax.inject.Inject

class GasEngine @Inject constructor(): Engine {

    override fun onStart() {
        printD("GasEngine Start")
    }

    override fun onStop() {
        printD("GasEngine stop")
    }
}