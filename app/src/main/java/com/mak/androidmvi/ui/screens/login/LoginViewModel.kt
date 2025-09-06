package com.mak.androidmvi.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import com.mak.androidmvi.core.manager.EventManager
import com.mak.androidmvi.core.manager.EventManager.AppEvent
import com.mak.androidmvi.ui.extensions.isValidEmail
import com.mak.androidmvi.ui.extensions.isValidPassword
import com.mak.androidmvi.ui.manager.SnackBarManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    //to be used for simple apps
    private val _state = mutableStateOf(LoginUiState())
    val state: State<LoginUiState> get() = _state

    //Mvi Recommened Pattren plus for complex data handling
    private val _stateMutableStateFlow = MutableStateFlow(LoginUiState())
    val stateFlow: StateFlow<LoginUiState> get()  = _stateMutableStateFlow.asStateFlow()


 /*  val isEmailValid: StateFlow<Boolean> = stateFlow.value.email
        .map { _state.value.email.isValidEmail() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    val isFormValid: StateFlow<Boolean> = _state
        .map { state ->
            state.email.isValidEmail() && state.password.isValidPassword()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )*/



/*    val isEmailValid: Flow<Boolean> = stateFlow.value.email.map { stateFlow.value.email.isValidEmail() } as Flow<Boolean>
    val isPasswordValid: Flow<Boolean> = stateFlow.value.password.map {stateFlow.value.password.isValidPassword() } as Flow<Boolean>

    val enableNextButton: StateFlow<Boolean> = combine(isEmailValid, isPasswordValid) { validEmail, validPassword ->
        validEmail && validPassword
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )*/

    private val _effect = Channel<LoginEffect>()
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EnterEmail -> {

                //first approach
                _stateMutableStateFlow.update {
                    it.copy(
                        email = intent.email, emailError = "HI value"
                    )
                }

                //second approach
                _state.value = _state.value.copy(email = intent.email, emailError = "HI value")
            }
            is LoginIntent.EnterPassword -> {

                //first approach
                _stateMutableStateFlow.update {
                    it.copy(
                        password = intent.password, passwordError = null
                    )
                }
                //second approach
                _state.value = _state.value.copy(password = intent.password, passwordError = null)
            }
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
            delay(1000)
            if (current.email == "user@example.com" && current.password == "password") {
                _state.value = current.copy(isLoading = false, isSuccess = true)
                navigate(LoginEffect.NavigateHome)
                //Show Snackbar
                EventManager.triggerEvent(AppEvent.ShowSnackbar("Login SuccessFull"))
                SnackBarManager.showMessageSharedFlow("Login SuccessFull")
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