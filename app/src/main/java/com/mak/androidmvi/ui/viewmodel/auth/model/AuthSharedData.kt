package com.mak.androidmvi.ui.viewmodel.auth.model

import com.mak.androidmvi.ui.core.baseviewmodel.model.BaseData

data class AuthSharedData(var currentStep: String ? = null) : BaseData() {

    override fun clear() {
        currentStep = null
    }
}

//class AuthViewModel(app: Application) : BaseViewModel<AuthSharedData>(app)