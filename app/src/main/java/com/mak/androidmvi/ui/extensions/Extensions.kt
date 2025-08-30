package com.mak.androidmvi.ui.extensions

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController

val NavController.CurrentDestination: NavDestination?
    get() = currentBackStackEntry?.destination


fun Modifier.safePadding(paddingValues: PaddingValues?): Modifier {
    return if (paddingValues != null) this.padding(paddingValues) else this
}

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
inline fun <reified VM : ViewModel> NavController.sharedViewModel(
    graphRoute: String? = null
): VM {
    val navBackStackEntry = remember(this) {
        if (graphRoute == null) {
            getBackStackEntry(graph.id) // scope to whole NavHost
        } else {
            getBackStackEntry(graphRoute) // scope to a specific subgraph
        }
    }
    return viewModel(navBackStackEntry)
}



/*
@Composable
inline fun <reified VM : ViewModel> NavController.sharedHiltViewModel(
    graphRoute: String? = null
): VM {
    val navBackStackEntry: NavBackStackEntry = remember(this) {
        if (graphRoute == null) {
            getBackStackEntry(graph.id) // scope to whole NavHost
        } else {
            getBackStackEntry(graphRoute) // scope to a specific subgraph
        }
    }
    return hiltViewModel(navBackStackEntry)
}
*/

/*
val parentEntry = remember(backStackEntry) {
    navController.getBackStackEntry("DashboardRoot")
}
val dashboardViewModel: DashboardViewModel = hiltViewModel(parentEntry)
*/

/*@Composable
fun rememberSharedViewModel(
    navController: NavHostController,
    viewModelStoreOwner: ViewModelStoreOwner = navController.getViewModelStoreOwner(
        navController.graph.id
    )
): SharedViewModel {
    return viewModel(viewModelStoreOwner = viewModelStoreOwner)
}*/

/*@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.sharedHiltViewModel(
    navController: NavHostController
): VM {
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navController.graph.id)
    }
    return hiltViewModel(parentEntry)
}*/
