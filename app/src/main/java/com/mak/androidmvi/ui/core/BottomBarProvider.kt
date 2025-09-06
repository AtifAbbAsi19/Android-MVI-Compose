package com.mak.androidmvi.ui.core

sealed class BottomBarType {
    object Visible : BottomBarType()
    object Hidden : BottomBarType()
}

object BottomBarProvider {
    // Define which routes should show bottom bar
    private val visibleRoutes = setOf("dashboard", "home", "profile")

    fun getBottomBarType(route: String?): BottomBarType {
        return if (route in visibleRoutes) {
            BottomBarType.Visible
        } else {
            BottomBarType.Hidden
        }
    }
}

/*
bottomBar = {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val bottomBarType = BottomBarProvider.getBottomBarType(currentRoute)

    when (bottomBarType) {
        is BottomBarType.Visible -> BottomNavigationBar(navController = navController)
        is BottomBarType.Hidden -> {}
    }
}*/
