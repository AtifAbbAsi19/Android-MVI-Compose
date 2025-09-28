package com.mak.androidmvi.ui.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mak.androidmvi.designsystem.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RootViewModel : ViewModel() {

    private val _uiEvents = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvents = _uiEvents.receiveAsFlow()

    fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }
}