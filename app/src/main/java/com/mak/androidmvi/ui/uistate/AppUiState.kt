package com.mak.androidmvi.ui.uistate

import androidx.compose.runtime.Immutable

@Immutable
data class AppUiState(
    val isDarkMode: Boolean = false,
    val rtl: Boolean = false,
    val isLoggedIn: Boolean = false,
    val appVersion: String = ""
)