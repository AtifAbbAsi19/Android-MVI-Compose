package com.mak.androidmvi.ui.screens.login

// Optional one-time effects
sealed class LoginEffect {
    object NavigateHome : LoginEffect()
    object NavigateSignup : LoginEffect()
    object NavigateForgotPassword : LoginEffect()
}