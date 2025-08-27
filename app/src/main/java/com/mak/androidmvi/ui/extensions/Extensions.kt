package com.mak.androidmvi.ui.extensions

import androidx.navigation.NavController
import androidx.navigation.NavDestination

val NavController.CurrentDestination: NavDestination?
    get() = currentBackStackEntry?.destination


