package com.mak.androidmvi.ui.screens.splash

import androidx.annotation.IdRes
import androidx.compose.runtime.Immutable
import com.mak.androidmvi.R


@Immutable
data class SplashState(
    val isLoading: Boolean = true,
    val logo : Int =  R.drawable.splash_logo
)