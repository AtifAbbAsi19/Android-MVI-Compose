package com.mak.androidmvi.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    /**
     * A private MutableStateFlow to store and update the UI state for the splash screen.
     */
    private val _uiState = MutableStateFlow(SplashState())

    /**
     * A public immutable StateFlow exposing the current UI state.
     * The UI observes this flow to react to state changes.
     */
    val uiState: StateFlow<SplashState> = _uiState.asStateFlow()



    private val _effect = Channel<SplashEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            onHandleIntent(SplashIntent.ShowLoading(isLoading = true))
            delay(3000) // 3-second splash
            //_state.value = _state.value.copy(isLoading = false)
            _effect.send(SplashEffect.NavigateToAuth)
        }
    }

    fun onHandleIntent(intent: SplashIntent) {
        when (intent) {
            SplashIntent.OnTimerFinished -> {
                viewModelScope.launch {
                    _effect.send(SplashEffect.NavigateToAuth)
                }
            }

            SplashIntent.TriggerTimer -> {
            }

            is SplashIntent.ShowLoading -> {
                //_uiState
            }
        }
    }


    sealed class SplashEffect {
        object NavigateToAuth : SplashEffect()
    }
}