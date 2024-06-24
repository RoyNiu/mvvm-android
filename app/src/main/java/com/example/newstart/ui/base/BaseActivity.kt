package com.example.newstart.ui.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Roy
 */

abstract class BaseActivity : AppCompatActivity() {

    abstract fun initData()
    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }



}