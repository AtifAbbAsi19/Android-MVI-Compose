package com.mak.androidmvi.ui.core.baseviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mak.androidmvi.ui.core.sharedviewmodel.data.SharedViewData

abstract class BaseViewModel<S : SharedViewData>(
    application: Application
) : AndroidViewModel(application) {

    private var _sharedData: S? = null
    val sharedData: S?
        get() = _sharedData

    fun updateSharedData(sharedData: S?) {
        _sharedData = sharedData
        onSharedDataUpdated(sharedData)
    }

    open fun onSharedDataUpdated(sharedData: S?) {}

    override fun onCleared() {
        _sharedData?.onCleared()
        _sharedData?.clear()
        _sharedData = null
        super.onCleared()
    }
}