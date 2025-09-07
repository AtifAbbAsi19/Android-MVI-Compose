package com.mak.androidmvi.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.mak.androidmvi.core.navigation.Destination
import com.mak.androidmvi.ui.core.sharedviewmodel.SharedViewModel
import com.mak.androidmvi.ui.core.sharedviewmodel.data.SharedViewData


inline fun <reified T : Destination> NavDestination?.isCurrent(): Boolean {
    return this?.route == T::class.qualifiedName
}

/*fun NavDestination?.isRoute(destination: Destination): Boolean {
    return this?.route == destination.route
}*/

@Composable
inline fun <reified S : SharedViewData, reified VM : SharedViewModel<S>> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
    navGraphRoute: String
): VM {
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}

//nav graph scoped view model
@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
    navGraphRoute: String
): VM {
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}