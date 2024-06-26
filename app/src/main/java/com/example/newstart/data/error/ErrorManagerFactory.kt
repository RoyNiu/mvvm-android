package com.example.newstart.data.error

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import com.example.newstart.domain.Error
import javax.inject.Inject

/**
 * Created by Roy
 */
class ErrorManagerFactory @Inject constructor(@ApplicationContext private val context: Context) {

    fun createErrorManager(featureErrorMap: Map<out Error, String>): ErrorManager {
        return object : ErrorManager(context) {
            override fun featureErrorMap(): Map<out Error, String> {
                return featureErrorMap
            }
        }
    }
}