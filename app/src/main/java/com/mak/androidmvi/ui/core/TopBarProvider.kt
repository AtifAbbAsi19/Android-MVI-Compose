package com.mak.androidmvi.ui.core

import androidx.navigation.NavDestination
import com.mak.androidmvi.core.navigation.Destination
import com.mak.androidmvi.ui.extensions.isCurrent

object TopBarProvider {

    // Define which routes should show different type of appbar toolbar


    fun getTopBarType(destination: NavDestination?): AppTopBarType {
        return when (destination) {
            Destination.Dashboard.Home -> AppTopBarType.Home
            Destination.Dashboard.Profile -> AppTopBarType.Small
            Destination.Dashboard.Settings -> AppTopBarType.Large
            else -> AppTopBarType.None
        }
    }

    fun getCurrentTopBarType(destination: NavDestination?): AppTopBarType {
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
    }
}

sealed class AppTopBarType {
    object Home : AppTopBarType()
    object Large : AppTopBarType()
    object Small : AppTopBarType()
    object None : AppTopBarType()
}

/*
topBar = {

}*/
