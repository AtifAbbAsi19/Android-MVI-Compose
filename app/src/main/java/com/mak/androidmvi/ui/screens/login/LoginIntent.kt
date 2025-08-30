package com.mak.androidmvi.ui.screens.login

// Represents user actions
sealed class LoginIntent {
    data class EnterEmail(val email: String) : LoginIntent()
    data class EnterPassword(val password: String) : LoginIntent()
    object SubmitLogin : LoginIntent()
    object NavigateToSignup : LoginIntent()
    object NavigateToForgotPassword : LoginIntent()
}