package com.mak.androidmvi

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mak.androidmvi.core.manager.EventManager
import com.mak.androidmvi.core.navigation.RootNavigationGraph
import com.mak.androidmvi.designsystem.AppScaffold
import com.mak.androidmvi.ui.core.AppScaffold
import com.mak.androidmvi.ui.extensions.AppConfig
import com.mak.androidmvi.ui.extensions.LocalAppConfig
import com.mak.androidmvi.ui.extensions.LocalNavController
import com.mak.androidmvi.ui.manager.SnackBarManager
import com.mak.androidmvi.ui.screens.splash.SplashViewModel
import com.mak.androidmvi.ui.theme.AndroidMviTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
//class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // Keep the splash screen visible
        // val splashScreen = installSplashScreen()
        //val splashViewModel = SplashViewModel()
        // Keep the splash screen on display until isLoading is false
        //  splashScreen.setKeepOnScreenCondition { splashViewModel.uiState.value.isLoading }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            // Initializes a navigation controller to handle navigation between screens.
            val rootNavController = rememberNavController()
            // Creates a state to manage snackbar messages.
            val snackbarHostState = remember { SnackbarHostState() }
            //App Context
            val context = LocalContext.current

            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                rememberTopAppBarState()
            )



            // Global back press handling
            BackHandler {
                if (rootNavController.previousBackStackEntry != null) {
                    rootNavController.popBackStack()
                } else {
                    finish() // exit app
                }
            }


            AndroidMviTheme {

                AppScaffold(
                    navController = rootNavController,
                    showTopBar = true,
                    showBottomBar = true,
                    scrollBehavior = scrollBehavior,
                    snackbarHostState = snackbarHostState
                    ) {

                    // Provide the app configuration for the entire composable hierarchy
                    CompositionLocalProvider(
                        LocalAppConfig provides AppConfig(isDebugMode = true),
                        LocalNavController provides rootNavController
                    ) {
                        RootNavigationGraph(rootNavController,scrollBehavior)
                    }
                }


            }
        }
    }
}


@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    minLines: Int = 1
) {
    var expanded by remember { mutableStateOf(false) }
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    var isTextOverflow by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = text,
            maxLines = if (expanded) Int.MAX_VALUE else minLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { layoutResult ->
                textLayoutResult = layoutResult
                if (!expanded) {
                    isTextOverflow = layoutResult.hasVisualOverflow
                }
            }
        )

        if (isTextOverflow || expanded) {
            TextButton(
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "See Less" else "See More")
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidMviTheme {
        Greeting("Android")
    }
}