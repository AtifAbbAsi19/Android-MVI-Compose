package com.mak.androidmvi.core.navigation

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mak.androidmvi.ui.screens.splash.SplashScreen
import com.mak.androidmvi.ui.viewmodel.AppSharedViewModel

fun NavGraphBuilder.splashNavGraph(navController: NavHostController){

    // Auth subgraph
    navigation<Destination.Root>(startDestination = Destination.Splash) {
        // Splash
        composable<Destination.Splash> { backStackEntry->


            //  Scope the SharedAuthViewModel to the Auth.Root graph
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Destination.Root)
            }

            val appSharedViewModel: AppSharedViewModel =   viewModel(parentEntry)   //hiltViewModel(parentEntry)


            SplashScreen(
                appSharedViewModel = appSharedViewModel,
                onFinished = { navController.navigate(Destination.Auth.Root) {
                    popUpTo(Destination.Splash) { inclusive = true }
                } }
            )
        }
    }
}