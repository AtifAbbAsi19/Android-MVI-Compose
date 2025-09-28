package com.mak.androidmvi.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mak.androidmvi.core.navigation.Destination

@Composable
inline fun <reified VM : ViewModel> navGraphViewModel(
    navController: NavHostController,
    route: Destination,
    backStackEntry : String
): VM {


    //  Scope the SharedAuthViewModel to the Auth.Root graph
    val backStackEntry = remember(backStackEntry) {
        navController.getBackStackEntry(Destination.Auth.Root)
    }

    return viewModel(backStackEntry)
}
