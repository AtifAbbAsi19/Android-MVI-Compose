package com.mak.androidmvi.core.navigation

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mak.androidmvi.ui.core.RootViewModel
import com.mak.androidmvi.ui.screens.notification.NotificationScreen
import com.mak.androidmvi.ui.screens.splash.SplashScreen
import com.mak.androidmvi.ui.viewmodel.AppSharedViewModel

fun NavGraphBuilder.notificationNavGraph(navController: NavHostController, rootViewModel: RootViewModel){

    //  subgraph
    navigation<Destination.NotificationGraph.Root>(startDestination = Destination.NotificationGraph.List) {

        // notification list
        composable<Destination.NotificationGraph.List> { backStackEntry->

            //  Scope the SharedAuthViewModel to the Auth.Root graph
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Destination.Root)
            }

            val appSharedViewModel: AppSharedViewModel =   viewModel(parentEntry)   //hiltViewModel(parentEntry)

            NotificationScreen()
        }
    }
}