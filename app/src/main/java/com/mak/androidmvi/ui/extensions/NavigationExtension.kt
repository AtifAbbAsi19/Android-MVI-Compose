package com.mak.androidmvi.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.mak.androidmvi.ui.core.sharedviewmodel.SharedViewModel
import com.mak.androidmvi.ui.core.sharedviewmodel.data.SharedViewData

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