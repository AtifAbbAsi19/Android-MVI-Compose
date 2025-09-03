package com.mak.androidmvi.ui.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController: NavHostController,
    topBar: (@Composable ((TopAppBarScrollBehavior?) -> Unit))? = null, // now nullable
    bottomBar: @Composable ((() -> Unit))? = null,
    showBottomBar: Boolean = false,
    content: @Composable (PaddingValues?, TopAppBarScrollBehavior?) -> Unit,
) {

    // Defines a scroll behavior for the top app bar, enabling it to collapse on scroll.
    // only create scrollBehavior if topBar is provided
    val scrollBehavior = topBar?.let {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            rememberTopAppBarState()
        )
    }

    // Creates a state to manage snackbar messages.
    val snackbarHostState = remember { SnackbarHostState() }


    // Observes the current back stack entry to determine the navigation state.
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    // Determines if the back button should be shown, based on the navigation stack.
    val showBackButton by remember(currentBackStackEntry) {
        derivedStateOf { navController.previousBackStackEntry != null }
    }

    //Modifier.safeDrawingPadding()

    // Defines the scaffold structure, which includes the top app bar, snackbar host, and floating action button.
    Scaffold(
        modifier = Modifier
            .safeDrawingPadding()
            .then(
                if (scrollBehavior != null) Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
                else Modifier
            ),
        snackbarHost = {
            snackbarHostState?.let {
                SnackbarHost(hostState = snackbarHostState)
            }
        }, // Host for displaying snackbars.
        topBar = {
            //MainTopAppBar(
            // )

            topBar?.invoke(scrollBehavior) // only call if topBar is provided
        },
        //Modifier.nestedScroll(scrollBehavior.nestedScrollConnection) // Ensures nested scrolling works with the top bar.
        bottomBar = {
            bottomBar?.let {
                bottomBar.invoke()
            } ?: run {
                if (showBottomBar)
                    BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .safeDrawingPadding()
                .navigationBarsPadding(),// Ensures padding for the scaffold's content area.
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                content(innerPadding, scrollBehavior)
            }
        }
    }
}