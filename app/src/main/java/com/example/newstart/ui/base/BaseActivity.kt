package com.example.newstart.ui.base

import android.os.Bundle
import androidx.activity.ComponentActivity

/**
 * Created by Roy
 */

abstract class BaseActivity : ComponentActivity() {

    abstract fun initViewModel()
    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initViewModel()
    }



}