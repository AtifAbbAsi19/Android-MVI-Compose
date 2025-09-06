package com.mak.androidmvi.ui.core

object TopBarProvider {

    fun getTopBarType(route: String?): AppTopBarType {
        return when (route) {
            "dashboard" -> AppTopBarType.Large
            "profile", "settings" -> AppTopBarType.Small
            else -> AppTopBarType.None
        }
    }
}

sealed class AppTopBarType {
    object Large : AppTopBarType()
    object Small : AppTopBarType()
    object None : AppTopBarType()
}

/*
topBar = {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val topBarType = TopBarProvider.getTopBarType(currentRoute)

    when (topBarType) {
        is AppTopBarType.Large -> LargeDashboardTopBar(scrollBehavior)
        is AppTopBarType.Small -> SmallDefaultTopBar(
            title = currentRoute?.replaceFirstChar { it.uppercase() } ?: "",
            scrollBehavior = scrollBehavior
        )
        is AppTopBarType.None -> {}
    }
}*/
