package com.mak.androidmvi.ui.screens.splash


sealed class SplashIntent {
    object OnTimerFinished : SplashIntent()
}
