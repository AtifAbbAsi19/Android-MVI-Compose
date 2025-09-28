package com.mak.androidmvi.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.mak.androidmvi.R
import com.mak.androidmvi.core.sharedViewModel
import com.mak.androidmvi.ui.core.AppScaffold
import com.mak.androidmvi.ui.core.BottomNavigationBar
import com.mak.androidmvi.ui.core.baseviewmodel.model.BaseData
import com.mak.androidmvi.ui.core.sharedviewmodel.SharedViewModel
import com.mak.androidmvi.ui.screens.home.HomeTopAppBar
import com.mak.androidmvi.ui.viewmodel.AppSharedViewModel
import kotlin.reflect.KClass


/**
 * Splash Screen
 * Auth Graph  (prelogin journey)
 * Post Login (Dashboard /Home)
 *
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootNavigationGraph() {

    // Observe the current back stack entry
    val rootNavController = rememberNavController()

    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentRoute by remember(navBackStackEntry) {
        derivedStateOf {
            navBackStackEntry?.destination?.route
        }
    }

    // Define the set of destination classes that should show the bottom bar
    val bottomBarRoutes = setOf<KClass<*>>(
        Destination.Dashboard.Home::class,
        // Add other bottom bar route classes here
    )

    // Check if the current destination has a route in the set of bottom bar routes
    val showBottomBar = currentDestination?.hierarchy?.any { destination ->
        bottomBarRoutes.any { it == destination.route?.javaClass?.kotlin }
    } ?: false

    // Or a more direct approach using hasRoute for each item,
    // which works well when you have a small, fixed number of routes.
    val showDashboardToolbar = currentDestination?.hasRoute<Destination.Dashboard.Home>() ?: false


    AppScaffold(
        navController = rootNavController,
        topBar = { scrollBehavior ->

            AnimatedVisibility(
                visible = showDashboardToolbar,
                enter = slideInVertically(
                    // Slide in from the bottom
                    initialOffsetY = { fullHeight -> fullHeight }
                ),
                exit = slideOutVertically(
                    // Slide out to the bottom
                    targetOffsetY = { fullHeight -> fullHeight }
                )
            ) {

                scrollBehavior?.let { scrollBehavior ->

                    HomeTopAppBar(
                        userName = "Atif",
                        balance = "$100",
                        profileImageRes = R.drawable.login,
                        scrollBehavior = scrollBehavior
                    )
                }
            }
        },
        bottomBar = {

            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(
                    // Slide in from the bottom
                    initialOffsetY = { fullHeight -> fullHeight }
                ),
                exit = slideOutVertically(
                    // Slide out to the bottom
                    targetOffsetY = { fullHeight -> fullHeight }
                )
            ) {
                BottomNavigationBar(navController = rootNavController)
            }
        },
        showBottomBar = true
    ) { innerPadding, scrollBehavior ->


        NavHost(
            modifier = Modifier.safeDrawingPadding(),
            navController = rootNavController,
            startDestination = Destination.Root,
           // enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
           //exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(700)) },
           //popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) },
           // popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(700)) }
        ) {

            splashNavGraph(rootNavController)

            authNavGraph(rootNavController)

            OtpNavGraph(rootNavController)

            dashboardNavGraph(rootNavController)

            userProfileSettingsNavGraph(rootNavController)

        }//end of NavHost
    }

}