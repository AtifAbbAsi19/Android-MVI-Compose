package com.mak.androidmvi.ui.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.mak.androidmvi.ui.core.baseviewmodel.BaseViewModel
import com.mak.androidmvi.ui.core.sharedviewmodel.SharedViewModel
import com.mak.androidmvi.ui.core.sharedviewmodel.data.SharedViewData

//class BaseActivity: ComponentActivity()
class BaseActivity<V : BaseViewModel<S>, S : SharedViewData> : ComponentActivity(){

    var sharedViewModel : SharedViewModel<S>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel = buildSharedViewModel()
    }


    fun buildSharedViewModel()  = ViewModelProvider(this)[SharedViewModel::class.java] as SharedViewModel<S>

}