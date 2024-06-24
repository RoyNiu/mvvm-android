package com.example.newstart.fortest

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ViewModelTest @Inject constructor() : ViewModel() {

    fun forTest(){
        printD("viewModel")
    }
}