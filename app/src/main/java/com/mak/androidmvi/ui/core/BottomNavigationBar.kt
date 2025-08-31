package com.mak.androidmvi.ui.core

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.safeDrawingPadding
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
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mak.androidmvi.core.model.BottomNavigationItem
import com.mak.androidmvi.core.navigation.Destination

@Composable
fun BottomNavigationBar(navController: NavHostController, selectedRoute : Destination?= null) {

    val items = getBottomNavigationList().filter { it.enabled }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val _selectedRoute : Destination? = selectedRoute

    NavigationBar(
        //Don't forget to apply the modifier inside your BottomBar composable
        modifier = Modifier
            .navigationBarsPadding()
            .safeDrawingPadding()
    ) {
        items.forEachIndexed { index, item ->

            val isSelected =  currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {

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
                            imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                }
            )
        }
    }

      // ✅ Ensure the NavHost starts on the given selectedIndex (if provided)
       LaunchedEffect(Unit) {
           _selectedRoute?.let {
               navController.navigate(_selectedRoute) {
                   popUpTo(navController.graph.startDestinationId) { inclusive = true }
                   launchSingleTop = true
               }
           }
       }
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
