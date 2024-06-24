package com.example.newstart.ui.base

import androidx.lifecycle.ViewModel
import com.example.newstart.data.error.ErrorManager
import javax.inject.Inject

/**
 * Created by Roy
 */
abstract class BaseViewModel: ViewModel() {

    @Inject
    lateinit var errorManager: ErrorManager
}