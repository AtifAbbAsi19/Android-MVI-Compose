package com.mak.androidmvi.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
inline fun <reified VM : ViewModel> navGraphViewModel(
    navController: NavHostController,
    route: String
): VM {
    val backStackEntry = remember(navController) {
        navController.getBackStackEntry(route)
    }
    return viewModel(backStackEntry)
}
