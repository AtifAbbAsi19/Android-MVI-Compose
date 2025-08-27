package com.mak.androidmvi.core.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mak.androidmvi.ui.screens.splash.SplashScreen

fun NavGraphBuilder.splashNavGraph(navController: NavHostController){

    // Auth subgraph
    navigation<Destination.Root>(startDestination = Destination.Splash) {
        // Splash
        composable<Destination.Splash> {
            SplashScreen(
                onFinished = { navController.navigate(Destination.Auth.Root) {
                    popUpTo(Destination.Splash) { inclusive = true }
                } }
            )
        }
    }
}