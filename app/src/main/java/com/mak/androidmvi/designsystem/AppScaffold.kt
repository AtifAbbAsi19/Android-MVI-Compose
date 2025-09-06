package com.mak.androidmvi.designsystem

import android.widget.Toast
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import com.mak.androidmvi.ui.core.BottomNavigationBar
import com.mak.androidmvi.ui.extensions.LocalContext
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController: NavHostController,
    topBar: (@Composable ((TopAppBarScrollBehavior?) -> Unit))? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    showBottomBar: Boolean = false,
    content: @Composable (PaddingValues?, TopAppBarScrollBehavior?) -> Unit,
) {
    val scrollBehavior = topBar?.let {
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
            rememberTopAppBarState()
        )
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Bottom sheet state
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var bottomSheetContent by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }

    // Collect global UiEvents
    LaunchedEffect(Unit) {
        UiController.events.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(event.message, event.action)
                    }
                }
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is UiEvent.ShowDialog -> {
                    // Could use a shared dialog state holder
                    bottomSheetContent = {
                        AlertDialog(
                            onDismissRequest = { bottomSheetContent = null },
                            confirmButton = {
                                TextButton(onClick = { bottomSheetContent = null }) {
                                    Text("OK")
                                }
                            },
                            title = { Text(event.title) },
                            text = { Text(event.message) }
                        )
                    }
                }
                is UiEvent.ShowBottomSheet -> {
                    bottomSheetContent = event.content
                }
                UiEvent.DismissBottomSheet -> {
                    bottomSheetContent = null
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .safeDrawingPadding()
            .then(if (scrollBehavior != null) Modifier.nestedScroll(scrollBehavior.nestedScrollConnection) else Modifier),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = { topBar?.invoke(scrollBehavior) },
        bottomBar = {
            bottomBar?.invoke() ?: run {
                if (showBottomBar) BottomNavigationBar(navController = navController)
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
                .navigationBarsPadding(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                content(innerPadding, scrollBehavior)

                // Render dialog or bottom sheet if needed
                bottomSheetContent?.let { content ->
                    ModalBottomSheet(
                        onDismissRequest = { bottomSheetContent = null },
                        sheetState = bottomSheetState
                    ) {
                        content()
                    }
                }
            }
        }
    }
}
