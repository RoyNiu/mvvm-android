package com.example.newstart.data.error

import com.example.newstart.domain.Error

/**
 * Created by Roy
 */
interface ErrorCase {

    fun getErrorMsg(e: Error):  String

}