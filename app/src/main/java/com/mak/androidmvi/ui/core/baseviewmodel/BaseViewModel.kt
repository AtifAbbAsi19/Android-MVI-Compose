package com.mak.androidmvi.ui.core.baseviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import com.mak.androidmvi.ui.core.sharedviewmodel.data.SharedViewData

abstract class BaseViewModel<S : SharedViewData>(
    context: Application
) : AndroidViewModel(context), DefaultLifecycleObserver {

    var sharedData: S? = null

    fun updateSharedData(sharedData: S?) {
        this.sharedData = sharedData
        onSharedDataUpdated()
    }

    open fun onSharedDataUpdated() {}
}