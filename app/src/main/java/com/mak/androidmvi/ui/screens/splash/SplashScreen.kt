package com.mak.androidmvi.ui.screens.splash

import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mak.androidmvi.R
import com.mak.androidmvi.core.asPainter
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {

    val viewModel: SplashViewModel = viewModel()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    val onFinishedState by rememberUpdatedState(onFinished)

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SplashViewModel.SplashEffect.NavigateToAuth -> onFinishedState()
            }
        }
    }


    // Start a coroutine when this composable enters the composition
    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds
        onFinishedState() // Navigate to next screen
    }

    // Your UI for Splash
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = uiState.logo.asPainter(),
            contentDescription = "splash_logo"
        )

      // if (uiState.isLoading) {
        //    CircularProgressIndicator()
        //}

        Text(
            text = uiState.version,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 24.dp)
        )

    }

}