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

    private val _state = mutableStateOf(LoginUiState())
    val state: State<LoginUiState> get() = _state

    private val _effect = Channel<LoginEffect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EnterEmail -> _state.value = _state.value.copy(email = intent.email, emailError = null)
            is LoginIntent.EnterPassword -> _state.value = _state.value.copy(password = intent.password, passwordError = null)
            LoginIntent.SubmitLogin -> submitLogin()
            LoginIntent.NavigateToSignup -> navigate(LoginEffect.NavigateSignup)
            LoginIntent.NavigateToForgotPassword -> navigate(LoginEffect.NavigateForgotPassword)
        }
    }

    private fun navigate(effect: LoginEffect) {
        viewModelScope.launch { _effect.send(effect) }
    }

    private fun submitLogin() {
        val current = _state.value

        val emailError = if (current.email.isBlank()) "Email is required" else null
        val passwordError = if (current.password.isBlank()) "Password is required" else null

        if (emailError != null || passwordError != null) {
            _state.value = current.copy(emailError = emailError, passwordError = passwordError)
            return
        }

        // Fake login check
        _state.value = current.copy(isLoading = true)
        viewModelScope.launch {
            // simulate network
            kotlinx.coroutines.delay(1000)
            if (current.email == "user@example.com" && current.password == "password") {
                _state.value = current.copy(isLoading = false, isSuccess = true)
                navigate(LoginEffect.NavigateHome)
            } else {
                _state.value = current.copy(isLoading = false, passwordError = "Invalid credentials")
            }
        }
    }

    fun validateEmailOnFocusLost() {
        val emailError = if (_state.value.email.isBlank()) "Email is required" else null
        _state.value = _state.value.copy(emailError = emailError)
    }

    fun validatePasswordOnFocusLost() {
        val passwordError = if (_state.value.password.isBlank()) "Password is required" else null
        _state.value = _state.value.copy(passwordError = passwordError)
    }
}