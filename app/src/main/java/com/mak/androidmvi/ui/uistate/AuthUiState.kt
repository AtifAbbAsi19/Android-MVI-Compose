package com.mak.androidmvi.ui.uistate

data class AuthUiState(
    val email: String = "",
    val isLoggedIn: Boolean = false,
    val pageInfo : String = "Root"
)