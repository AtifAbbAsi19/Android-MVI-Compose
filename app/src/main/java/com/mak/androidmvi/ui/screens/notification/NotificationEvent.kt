package com.mak.androidmvi.ui.screens.notification

import com.mak.androidmvi.ui.core.model.NotificationItem

sealed class NotificationEvent {
    data class NotificationClicked(val item: NotificationItem) : NotificationEvent()
    data class DeleteNotification(val item: NotificationItem) : NotificationEvent()
    object MarkAllAsRead : NotificationEvent()
}