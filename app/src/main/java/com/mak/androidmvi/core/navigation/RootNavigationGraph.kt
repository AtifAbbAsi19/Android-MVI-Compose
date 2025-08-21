package com.mak.androidmvi.core.navigation

import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost


/**
 * Splash Screen
 * Auth Graph  (prelogin journey)
 * Post Login (Dashboard /Home)
 *
 */

@Composable
fun RootNavigationGraph(navController: NavHostController) {

    NavHost(
        modifier = Modifier.safeDrawingPadding(),
        navController = navController,
        startDestination = Destination.Root,
    ) {

        splashNavGraph(navController)

        authNavGraph(navController)

        dashboardNavGraph(navController)

        userProfileSettingsNavGraph(navController)


    }//end of NavHost
}