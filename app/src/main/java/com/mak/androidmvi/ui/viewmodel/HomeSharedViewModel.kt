package com.mak.androidmvi.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mak.androidmvi.ui.uistate.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeSharedViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState



}

