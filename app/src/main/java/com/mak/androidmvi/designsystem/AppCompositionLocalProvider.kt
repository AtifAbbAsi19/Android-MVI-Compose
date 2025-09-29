package com.mak.androidmvi.designsystem

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.mak.androidmvi.ui.extensions.AppConfig
import com.mak.androidmvi.ui.extensions.LocalAppConfig
import com.mak.androidmvi.ui.extensions.LocalNavController

//https://composables.com/docs/androidx.compose.material3/material3/components/LinearWavyProgressIndicator
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppCompositionLocalProvider(
    content: @Composable (PaddingValues?, TopAppBarScrollBehavior?) -> Unit
){

  /*  //App Context
    val context = LocalContext.current
    // Provide the app configuration for the entire composable hierarchy
    CompositionLocalProvider(
        LocalAppConfig provides AppConfig(isDebugMode = true),
        LocalNavController provides rootNavController,
        LocalContext provides context
    ) {

        content.invoke()
    }*/
}