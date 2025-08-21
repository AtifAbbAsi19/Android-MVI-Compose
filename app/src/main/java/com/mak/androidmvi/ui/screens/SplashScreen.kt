package com.mak.androidmvi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mak.androidmvi.R
import com.mak.androidmvi.core.asPainter
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {

    val viewModel: SplashViewModel = viewModel()

   // val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SplashViewModel.SplashEffect.NavigateToAuth -> onFinished()
            }
        }
    }


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

       /* if (state.isLoading) {
            CircularProgressIndicator()
        }*/

        Image(
            painter = R.drawable.splash_logo.asPainter(),
            contentDescription = ""
        )
    }

}