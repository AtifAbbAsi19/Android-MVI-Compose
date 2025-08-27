package com.mak.androidmvi.ui.screens.splash


sealed interface SplashIntent {
    data class ShowLoading(val isLoading: Boolean) : SplashIntent
    object TriggerTimer : SplashIntent
    object OnTimerFinished : SplashIntent
}
