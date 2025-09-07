package com.mak.androidmvi.ui.extensions

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

// 1. Create a data class for your configuration
data class AppConfig(
    val primaryPadding: Dp = 16.dp,
    val isDebugMode: Boolean = false
)



// 2. Define a CompositionLocal with a default value
val LocalAppConfig = compositionLocalOf { AppConfig() }

val LocalNavController = staticCompositionLocalOf<NavHostController?> { error("No Nav Host Controller Found") }
val LocalAppContext = staticCompositionLocalOf<Context?> { error("App Context not Found") }


val LocalSnackbarHostState = staticCompositionLocalOf<SnackbarHostState?> { error("App Context not Found") }
@OptIn(ExperimentalMaterial3Api::class)
val LocalTopAppBarScrollBehavior = staticCompositionLocalOf<TopAppBarScrollBehavior?> { error("App Context not Found") }
