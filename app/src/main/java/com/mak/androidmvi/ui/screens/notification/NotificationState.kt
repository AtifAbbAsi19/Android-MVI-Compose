package com.mak.androidmvi.ui.screens.notification

import com.mak.androidmvi.ui.core.model.NotificationItem

data class NotificationState(
    val notifications: List<NotificationItem> = emptyList(),
    val isLoading: Boolean = false
)