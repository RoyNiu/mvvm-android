package com.example.newstart.ui.base

import androidx.lifecycle.ViewModel
import com.example.newstart.data.error.ErrorCase
import com.example.newstart.data.error.ErrorManagerFactory
import com.example.newstart.domain.Error
import javax.inject.Inject

/**
 * Created by Roy
 */
abstract class BaseViewModel : ViewModel() {

    @Inject
    lateinit var errorManagerFactory: ErrorManagerFactory

    private val errorManager: ErrorCase by lazy {
        errorManagerFactory.createErrorManager(getFeatureErrorMap())
    }

    fun getErrorStr(e: Error): String {
        return errorManager.getErrorMsg(e);
    }

    open fun getFeatureErrorMap(): Map<out Error, String> {
        return mapOf()
    }

}