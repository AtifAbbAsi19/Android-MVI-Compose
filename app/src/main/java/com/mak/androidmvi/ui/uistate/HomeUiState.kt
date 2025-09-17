package com.mak.androidmvi.ui.uistate

import androidx.compose.runtime.Immutable

@Immutable
data class HomeUiState(
    val email: String = "",
    val isLoggedIn: Boolean = false,
    val pageInfo : String = ""
)