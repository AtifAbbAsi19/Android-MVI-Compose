package com.mak.androidmvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mak.androidmvi.core.model.BottomNavigationItem
import com.mak.androidmvi.core.navigation.RootNavigationGraph
import com.mak.androidmvi.ui.theme.AndroidMviTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            val navController = rememberNavController()

            AndroidMviTheme {
                RootNavigationGraph(navController)
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