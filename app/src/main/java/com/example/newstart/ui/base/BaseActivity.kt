package com.example.newstart.ui.base

import android.os.Bundle
import androidx.activity.ComponentActivity

/**
 * Created by Roy
 */

abstract class BaseActivity : ComponentActivity() {

    abstract fun initData()
    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }



}