package com.mak.androidmvi.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mak.androidmvi.R
import com.mak.androidmvi.core.asPainter
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(onFinished: () -> Unit) {


    // Start a coroutine when this composable enters the composition
    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds
        onFinished() // Navigate to next screen
    }

    // Your UI for Splash
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
            Image(
                painter = R.drawable.splash_logo.asPainter(),
                contentDescription = ""
            )
    }

}