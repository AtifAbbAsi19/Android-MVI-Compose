package com.mak.androidmvi.ui.screens.login

import androidx.compose.runtime.Stable

// Represents the UI state
@Stable
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
){
    val emailLabel = "Email"
    val emailHint = "Enter Email"
    val passwordLabel = "Password"
    val reConfirmPasswordLabel = "Password"

    val isLoginEnabled: Boolean
        get() = email.isNotBlank() && password.isNotBlank() && !isLoading
}