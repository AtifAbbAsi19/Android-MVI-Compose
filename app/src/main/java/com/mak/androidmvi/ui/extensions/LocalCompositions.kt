package com.mak.androidmvi.ui.extensions

import android.content.Context
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
//val LocalContext = staticCompositionLocalOf<Context?> { error("App Context not Found") }