package com.mak.androidmvi.ui.screens.notification

import androidx.lifecycle.ViewModel
import com.mak.androidmvi.ui.core.model.NotificationItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NotificationViewModel : ViewModel() {

    private val _state = MutableStateFlow(NotificationState())
    val state: StateFlow<NotificationState> = _state.asStateFlow()

    init {
        // preload dummy data
        _state.update {
            it.copy(
                notifications = listOf(
                    NotificationItem("1", "New Message", "You’ve received a new message", "2m ago"),
                    NotificationItem("2", "Payment Successful", "Your payment of $120 has been processed", "1h ago"),
                    NotificationItem("3", "System Update", "A new version of the app is available", "Yesterday", isRead = true)
                )
            )
        }
    }

    fun onEvent(event: NotificationEvent) {
        when (event) {
            is NotificationEvent.NotificationClicked -> {
                markAsRead(event.item.id)
            }
            is NotificationEvent.DeleteNotification -> {
                _state.update {
                    it.copy(notifications = it.notifications.filterNot { n -> n.id == event.item.id })
                }
            }
            is NotificationEvent.MarkAllAsRead -> {
                _state.update {
                    it.copy(notifications = it.notifications.map { n -> n.copy(isRead = true) })
                }
            }
        }
    }

    private fun markAsRead(id: String) {
        _state.update {
            it.copy(
                notifications = it.notifications.map { n ->
                    if (n.id == id) n.copy(isRead = true) else n
                }
            )
        }
    }
}
