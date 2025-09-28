package com.mak.androidmvi.core.navigation

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mak.androidmvi.R
import com.mak.androidmvi.designsystem.UiEvent
import com.mak.androidmvi.ui.core.AppScaffold
import com.mak.androidmvi.ui.core.BottomNavigationBar
import com.mak.androidmvi.ui.core.RootViewModel
import com.mak.androidmvi.ui.screens.home.HomeTopAppBar
import kotlin.reflect.KClass


/**
 * Splash Screen
 * Auth Graph  (prelogin journey)
 * Post Login (Dashboard /Home)
 *
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootNavigationGraph(rootViewModel: RootViewModel = viewModel()) {

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


    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // Local states for dialog & bottom sheet
    var dialogState by remember { mutableStateOf<UiEvent.ShowDialog?>(null) }
    var bottomSheetContent by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    // Collect one-off events
    LaunchedEffect(Unit) {
        rootViewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel
                    )
                }

                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                is UiEvent.ShowDialog -> {
                    dialogState = event
                }

                UiEvent.DismissDialog -> {
                    dialogState = null
                }

                is UiEvent.ShowBottomSheet -> {
                    bottomSheetContent = event.content
                }

                UiEvent.DismissBottomSheet -> {
                    bottomSheetContent = null
                }

                is UiEvent.Navigate -> {
                    rootNavController.navigate(event.route)
                }
            }
        }
    }



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



            splashNavGraph(rootNavController , rootViewModel)

            notificationNavGraph(rootNavController , rootViewModel)

            authNavGraph(rootNavController, rootViewModel)

            OtpNavGraph(rootNavController, rootViewModel)

            dashboardNavGraph(rootNavController, rootViewModel)

            userProfileSettingsNavGraph(rootNavController, rootViewModel)

        }//end of NavHost
    }



    // ---------- Dialog Handling ----------
    dialogState?.let { dialog ->
        AlertDialog(
            onDismissRequest = { rootViewModel.sendEvent(UiEvent.DismissDialog) },
            title = { Text(dialog.title) },
            text = { Text(dialog.message) },
            confirmButton = {
                TextButton(onClick = { rootViewModel.sendEvent(UiEvent.DismissDialog) }) {
                    Text("OK")
                }
            }
        )
    }

    // ---------- BottomSheet Handling ----------
    if (bottomSheetContent != null) {
        ModalBottomSheet(
            onDismissRequest = { rootViewModel.sendEvent(UiEvent.DismissBottomSheet) },
            sheetState = bottomSheetState
        ) {
            bottomSheetContent?.invoke()
        }
    }

}