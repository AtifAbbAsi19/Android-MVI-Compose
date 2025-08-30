package com.mak.androidmvi.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class LoginViewModel : ViewModel() {

    // Correctly define state
    private var _state = mutableStateOf(LoginUiState())
    val state: State<LoginUiState> get() = _state


    private val _effect = Channel<LoginEffect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EnterEmail -> {
                _state.value = _state.value.copy(email = intent.email, error = null)
            }
            is LoginIntent.EnterPassword -> {
                _state.value = _state.value.copy(password = intent.password, error = null)
            }
            LoginIntent.SubmitLogin -> {
                loginUser()
            }
            LoginIntent.NavigateToSignup -> {
                viewModelScope.launch { _effect.send(LoginEffect.NavigateSignup) }
            }
            LoginIntent.NavigateToForgotPassword -> {
                viewModelScope.launch { _effect.send(LoginEffect.NavigateForgotPassword) }
            }
        }
    }

    private fun loginUser() {
        val current = _state.value
        if (current.email == "user@example.com" && current.password == "password") {
            _state.value = current.copy(isLoading = false, error = null, isSuccess = true)
            viewModelScope.launch { _effect.send(LoginEffect.NavigateHome) }
        } else {
            _state.value = current.copy(isLoading = false, error = "Invalid credentials")
        }
    }
}