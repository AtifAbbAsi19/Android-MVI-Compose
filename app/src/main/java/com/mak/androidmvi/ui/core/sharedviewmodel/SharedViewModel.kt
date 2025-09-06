package com.mak.androidmvi.ui.core.sharedviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mak.androidmvi.ui.core.sharedviewmodel.data.SharedViewData
import kotlin.properties.ReadWriteProperty

class SharedViewModel<S: SharedViewData?> : ViewModel() {

    var cachedData by mutableStateOf<S?>(null)
        private set

    fun setData(data: S) {
        cachedData = data
    }

    fun clearData() {
        cachedData?.clear()
        cachedData = null
    }

    override fun onCleared() {
        super.onCleared()
        cachedData?.onCleared()
        cachedData = null
        println("SharedViewModel cleared ✅")
    }
}