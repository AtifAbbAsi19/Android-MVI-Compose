package com.mak.androidmvi.ui.screens


sealed class SplashIntent {
    object OnTimerFinished : SplashIntent()
}
