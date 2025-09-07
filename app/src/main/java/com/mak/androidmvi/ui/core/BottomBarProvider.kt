package com.mak.androidmvi.ui.core

import androidx.navigation.NavDestination
import com.mak.androidmvi.core.navigation.Destination
import com.mak.androidmvi.ui.extensions.isCurrent

sealed class BottomBarType {
    object Visible : BottomBarType()
    object Hidden : BottomBarType()
}

object BottomBarProvider {
    // Define which routes should show bottom bar
    private val visibleRoutes = setOf(
        Destination.Dashboard.Home.toString(),
        Destination.Dashboard.Profile.toString(),
        Destination.Dashboard.Settings.toString(),
        Destination.Dashboard.Chat.toString(),
        Destination.Dashboard.Search.toString()
    )

    fun getBottomBarType(destination: NavDestination?): BottomBarType {
        return if (destination?.route.toString() in visibleRoutes) {
            BottomBarType.Visible
        } else {
            BottomBarType.Hidden
        }
    }

 /*   fun getCurrentBottomBarType(destination: NavDestination?): BottomBarType {

        return if (destination?.cr in visibleRoutes) {
            BottomBarType.Visible
        } else {
            BottomBarType.Hidden
        }

        return when {
            destination.isCurrent<Destination.Dashboard.Home>() -> {
                AppTopBarType.Home
            }
            destination.isCurrent<Destination.Dashboard.Profile>() -> {
                AppTopBarType.Small
            }
            destination.isCurrent<Destination.Dashboard.Settings>() -> {
                AppTopBarType.Large
            }
            else -> AppTopBarType.None
        }
    }*/
}
