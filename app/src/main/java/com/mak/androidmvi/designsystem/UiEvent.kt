package com.mak.androidmvi.designsystem

import androidx.compose.runtime.Composable

sealed class UiEvent {
    data class ShowSnackbar(val message: String, val actionLabel: String? = null) : UiEvent()
    data class ShowToast(val message: String) : UiEvent()
    data class ShowDialog(val title: String, val message: String) : UiEvent()
    object DismissDialog : UiEvent()
    data class ShowBottomSheet(val content: @Composable () -> Unit) : UiEvent()
    object DismissBottomSheet : UiEvent()

    //not required
    data class Navigate(val route: String) : UiEvent()
}

