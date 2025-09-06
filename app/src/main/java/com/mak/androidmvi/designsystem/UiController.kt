package com.mak.androidmvi.designsystem

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object UiController {
    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    suspend fun send(event: UiEvent) {
        _events.emit(event)
    }
}