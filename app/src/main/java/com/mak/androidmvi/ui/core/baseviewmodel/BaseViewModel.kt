package com.mak.androidmvi.ui.core.baseviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mak.androidmvi.ui.core.sharedviewmodel.data.SharedViewData

abstract class BaseViewModel<S : SharedViewData>(
    application: Application
) : AndroidViewModel(application), DefaultLifecycleObserver {

    private var _sharedData: S? = null
    val sharedData: S?
        get() = _sharedData

    fun updateSharedData(sharedData: S?) {
        _sharedData = sharedData
        onSharedDataUpdated(sharedData)
    }

    open fun onSharedDataUpdated(sharedData: S?) {}

    override fun onDestroy(owner: LifecycleOwner) {
        // Clear data when NavGraph lifecycle is destroyed
        _sharedData?.onCleared()
        _sharedData?.clear()
        _sharedData = null
        super.onDestroy(owner)
    }

    override fun onCleared() {
        // When ViewModel is removed from memory
        _sharedData?.onCleared()
        _sharedData?.clear()
        _sharedData = null
        super.onCleared()
    }
}