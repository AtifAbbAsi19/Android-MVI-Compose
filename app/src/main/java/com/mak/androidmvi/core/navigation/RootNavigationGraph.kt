package com.mak.androidmvi.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.createGraph
import com.mak.androidmvi.core.sharedViewModel
import com.mak.androidmvi.ui.viewmodel.AppSharedViewModel


/**
 * Splash Screen
 * Auth Graph  (prelogin journey)
 * Post Login (Dashboard /Home)
 *
 */

@Composable
fun RootNavigationGraph(navController: NavHostController) {

/*
    val graph =
        navController.createGraph(startDestination = Screen.Home.rout) {
            composable(route = StartingRoute) {

            }

        }
    NavHost(
        navController = navController,
        graph = graph,
        modifier = Modifier.padding(innerPadding)
    )*/

    NavHost(
        modifier = Modifier.safeDrawingPadding(),
        navController = navController,
        startDestination = Destination.Root,
/*        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },*/
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) }
    ) {

       // val appSharedViewModel = it.sharedViewModel<AppSharedViewModel>( navController = navController)


        splashNavGraph(navController)

        authNavGraph(navController)

        dashboardNavGraph(navController)

        userProfileSettingsNavGraph(navController)


    }//end of NavHost
}