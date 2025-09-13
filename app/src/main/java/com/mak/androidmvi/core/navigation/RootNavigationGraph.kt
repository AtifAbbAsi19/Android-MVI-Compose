package com.mak.androidmvi.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.createGraph
import com.mak.androidmvi.core.sharedViewModel
import com.mak.androidmvi.ui.core.baseviewmodel.model.BaseData
import com.mak.androidmvi.ui.core.sharedviewmodel.SharedViewModel
import com.mak.androidmvi.ui.viewmodel.AppSharedViewModel


/**
 * Splash Screen
 * Auth Graph  (prelogin journey)
 * Post Login (Dashboard /Home)
 *
 */

@Composable
fun RootNavigationGraph(navController: NavHostController) {

  /*  val backStackEntry by navController.currentBackStackEntryAsState()
    val rootEntry = remember(backStackEntry) {
        navController.getBackStackEntry(Destination.Root)
    }
    val mainSharedVM: SharedViewModel<BaseData> = viewModel(rootEntry)

*/
    NavHost(
        modifier = Modifier.safeDrawingPadding(),
        navController = navController,
        startDestination = Destination.Root,
/*        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },*/
       // popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) }
    ) {

        splashNavGraph(navController)

        authNavGraph(navController)

        OtpNavGraph(navController)

        dashboardNavGraph(navController)

        userProfileSettingsNavGraph(navController)

    }//end of NavHost
}