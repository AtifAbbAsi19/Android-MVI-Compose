package com.mak.androidmvi.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _state = mutableStateOf(SplashState())
    val state: State<SplashState> = _state

    private val _effect = Channel<SplashEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            delay(3000) // 3-second splash
            _state.value = _state.value.copy(isLoading = false)
            _effect.send(SplashEffect.NavigateToAuth)
        }
    }

    fun onEvent(event: SplashIntent) {
        when (event) {
            SplashIntent.OnTimerFinished -> {
                viewModelScope.launch {
                    _effect.send(SplashEffect.NavigateToAuth)
                }
            }
        }
    }


    sealed class SplashEffect {
        object NavigateToAuth : SplashEffect()
    }
}