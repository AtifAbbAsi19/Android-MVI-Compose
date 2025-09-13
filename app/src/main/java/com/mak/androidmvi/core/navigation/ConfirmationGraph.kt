package com.mak.androidmvi.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mak.androidmvi.ui.screens.success.PaymentSuccessScreen


fun NavGraphBuilder.paymentConfirmationGraph(navController: NavHostController){

    // Auth subgraph
    navigation<Destination.Root>(startDestination = Destination.Splash) {
        // Splash
        composable<Destination.Splash> {
            PaymentSuccessScreen()
        }
    }
}
