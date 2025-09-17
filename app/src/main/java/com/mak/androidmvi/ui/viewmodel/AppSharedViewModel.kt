package com.mak.androidmvi.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mak.androidmvi.ui.uistate.AppUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppSharedViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState

}