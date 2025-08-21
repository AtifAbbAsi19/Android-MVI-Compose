package com.mak.androidmvi.ui.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.mak.androidmvi.core.model.BottomNavigationItem
import com.mak.androidmvi.core.navigation.Destination

@Composable
fun DashboardScaffold(navController: NavHostController, content: @Composable () -> Unit) {

    Scaffold(
        modifier = Modifier.safeDrawingPadding(),
        bottomBar = {
            NavigationBar(
                //Don't forget to apply the modifier inside your BottomBar composable
                modifier = Modifier.navigationBarsPadding().safeDrawingPadding()
            ) {
                BottomNavigationBar(selectedIndex = 0,navController)
            }
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier) {
                content()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedIndex: Int, navController: NavHostController){

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(selectedIndex)
    }

    NavigationBar {
        getBottomNavigationList().forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index

                    when(index){
                        0->{
                            navController.navigate(Destination.Dashboard.Home)
                        }
                        1->{
                            navController.navigate(Destination.Dashboard.Profile)
                        }
                        2->{
                            navController.navigate(Destination.Dashboard.Settings)
                        }
                    }

                },
                label = {
                    Text(text = item.title)
                },
                alwaysShowLabel = false,
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount != null) {
                                Badge {
                                    Text(text = item.badgeCount.toString())
                                }
                            } else if (item.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (index == selectedItemIndex) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun getBottomNavigationList() = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false,

    ),
    BottomNavigationItem(
        title = "Services",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
        hasNews = false,
    ),
    BottomNavigationItem(
        title = "Chat",
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email,
        hasNews = false,
        badgeCount = 45
    ),
    BottomNavigationItem(
        title = "Settings",
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
