package com.mak.androidmvi.ui.core

import androidx.collection.mutableLongLongMapOf
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mak.androidmvi.core.model.BottomNavigationItem
import com.mak.androidmvi.core.navigation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScaffold(
    navController: NavHostController,
    topBar: (@Composable ((TopAppBarScrollBehavior?) -> Unit))? = null, // now nullable
    content: @Composable (PaddingValues?, TopAppBarScrollBehavior?) -> Unit
) {

   // Defines a scroll behavior for the top app bar, enabling it to collapse on scroll.
    // only create scrollBehavior if topBar is provided
    val scrollBehavior = topBar?.let {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            rememberTopAppBarState()
        )
    }

    // Creates a state to manage snackbar messages.
    val snackbarHostState = remember { SnackbarHostState() }


    // Observes the current back stack entry to determine the navigation state.
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    // Determines if the back button should be shown, based on the navigation stack.
    val showBackButton by remember(currentBackStackEntry) {
        derivedStateOf { navController.previousBackStackEntry != null }
    }

    //Modifier.safeDrawingPadding()

    // Defines the scaffold structure, which includes the top app bar, snackbar host, and floating action button.
    Scaffold(
        modifier  = Modifier
            .safeDrawingPadding()
        .then(
            if (scrollBehavior != null) Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
            else Modifier
        ),
        snackbarHost = {
            snackbarHostState?.let {
                SnackbarHost(hostState = snackbarHostState)
            }
        }, // Host for displaying snackbars.
        topBar = {
            //MainTopAppBar(
            // )

            topBar?.invoke(scrollBehavior) // only call if topBar is provided
        },
        //Modifier.nestedScroll(scrollBehavior.nestedScrollConnection) // Ensures nested scrolling works with the top bar.
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .windowInsetsPadding(WindowInsets.safeDrawing)
            ,// Ensures padding for the scaffold's content area.
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                content(innerPadding, scrollBehavior)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val items = getBottomNavigationList().filter { it.enabled }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    NavigationBar(
        //Don't forget to apply the modifier inside your BottomBar composable
        modifier = Modifier
            .navigationBarsPadding()
            .safeDrawingPadding()
    ) {
        items.forEachIndexed { index, item ->

            // ✅ Active tab depends only on NavController
            val selected = currentRoute == item.route || item.isSelected
            item.isSelected = selected
            NavigationBarItem(
                selected = selected,
                onClick = {

                    item.isSelected = true
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = { Text(text = item.title) },
                alwaysShowLabel = false,
                icon = {
                    BadgedBox(
                        badge = {
                            when {
                                item.badgeCount != null -> Badge {
                                    Text(text = item.badgeCount.toString())
                                }

                                item.hasNews -> Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                }
            )
        }
    }

    /*   // ✅ Ensure the NavHost starts on the given selectedIndex (if provided)
       LaunchedEffect(Unit) {
           selectedIndex?.let {
               navController.navigate(items[it].route) {
                   popUpTo(navController.graph.startDestinationId) { inclusive = true }
                   launchSingleTop = true
               }
           }
       }*/
}

@Composable
fun getBottomNavigationList() = listOf(
    BottomNavigationItem(
        title = "Home",
        route = Destination.Dashboard.Home,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false,
        isSelected = true
    ),
    BottomNavigationItem(
        title = "Services",
        route = Destination.Dashboard.Search,
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
        hasNews = false,
    ),
    BottomNavigationItem(
        title = "Profile",
        route = Destination.Dashboard.Chat,
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email,
        hasNews = false,
        badgeCount = 45
    ),
    BottomNavigationItem(
        title = "Settings",
        route = Destination.Dashboard.Settings,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        hasNews = true,
    ),


    )


/*
NavigationBarItem(
selected = false,
onClick = { navController.navigate(Destination.Dashboard.Home) },
icon = { Icon(Icons.Default.Home, null) },
label = { Text("Home") }
)
NavigationBarItem(
selected = false,
onClick = { navController.navigate(Destination.Dashboard.Profile) },
icon = { Icon(Icons.Default.Person, null) },
label = { Text("Profile") }
)
NavigationBarItem(
selected = false,
onClick = { navController.navigate(Destination.Dashboard.Settings) },
icon = { Icon(Icons.Default.Settings, null) },
label = { Text("Settings") }
)*/
