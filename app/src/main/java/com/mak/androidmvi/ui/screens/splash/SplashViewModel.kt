package com.mak.androidmvi.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {



    /**
     * A private MutableStateFlow to store and update the UI state for the notes screen.
     */
    private val _notesViewState = MutableStateFlow(SplashState())

    /**
     * A public immutable StateFlow exposing the current UI state.
     * The UI observes this flow to react to state changes.
     */
    val notesViewState: StateFlow<SplashState> = _notesViewState


  /*  private val _state = mutableStateOf(SplashState())
    val state: State<SplashState> = _state
*/
/*
    val state: StateFlow<State> by lazy {
        _state.onStart {
            // Load initial data here
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = initialState
        )
    }*/

    private val _effect = Channel<SplashEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            delay(3000) // 3-second splash
            //_state.value = _state.value.copy(isLoading = false)
            _effect.send(SplashEffect.NavigateToAuth)
        }
    }

    fun onIntent(event: SplashIntent) {
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