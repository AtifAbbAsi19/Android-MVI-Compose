package com.mak.androidmvi.ui.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    var cachedData by mutableStateOf("")
        private set

    fun setData(data: String) {
        cachedData = data
    }

    override fun onCleared() {
        super.onCleared()
        println("SharedFlowViewModel cleared ✅")
    }
}