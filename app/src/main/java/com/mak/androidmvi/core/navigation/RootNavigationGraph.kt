package com.mak.androidmvi.core.navigation

import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootNavigationGraph(navController: NavHostController,scrollBehavior: TopAppBarScrollBehavior?) {

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

        dashboardNavGraph(navController,scrollBehavior)

        userProfileSettingsNavGraph(navController)

    }//end of NavHost
}