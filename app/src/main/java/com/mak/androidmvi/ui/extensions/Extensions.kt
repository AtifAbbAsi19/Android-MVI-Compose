package com.mak.androidmvi.ui.extensions

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController

val NavController.CurrentDestination: NavDestination?
    get() = currentBackStackEntry?.destination

/*
val AppNavController: NavHostController?
    @Composable
    get() = LocalNavHostController.current
*/


fun Modifier.onlyOncePadding(
    start: Dp = 0.dp,
    top: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp
): Modifier {
    // Check if already contains MarkedPaddingModifier
    return if (this.any { it is MarkedPaddingModifierOnlyOnce }) this
    else this.then(MarkedPaddingModifierOnlyOnce(start, top, end, bottom))
}

private class MarkedPaddingModifierOnlyOnce(
    val start: Dp,
    val top: Dp,
    val end: Dp,
    val bottom: Dp
) : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: androidx.compose.ui.unit.Constraints
    ): MeasureResult {
        val horizontal = (start + end).roundToPx()
        val vertical = (top + bottom).roundToPx()
        val placeable = measurable.measure(constraints.offset(-horizontal, -vertical))
        return layout(placeable.width + horizontal, placeable.height + vertical) {
            placeable.placeRelative(start.roundToPx(), top.roundToPx())
        }
    }
}

//to check if padding is applied already or not
fun Modifier.markedPadding(padding: PaddingValues): Modifier =
    this.then(MarkedPaddingModifier(padding))

private class MarkedPaddingModifier(
    private val padding: PaddingValues
) : LayoutModifier {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {
        val placeable = measurable.measure(constraints)
        return layout(
            placeable.width + padding.calculateLeftPadding(LayoutDirection.Ltr).roundToPx()
                    + padding.calculateRightPadding(LayoutDirection.Ltr).roundToPx(),
            placeable.height
        ) {
            placeable.placeRelative(0, 0)
        }
    }
}


fun Modifier.safePadding(paddingValues: PaddingValues?): Modifier {
    return if (paddingValues != null) this.padding(paddingValues) else this
}

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
inline fun <reified VM : ViewModel> NavController.sharedViewModel(
    graphRoute: String? = null
): VM {
    val navBackStackEntry = remember(this) {
        if (graphRoute == null) {
            getBackStackEntry(graph.id) // scope to whole NavHost
        } else {
            getBackStackEntry(graphRoute) // scope to a specific subgraph
        }
    }
    return viewModel(navBackStackEntry)
}


/*
@Composable
inline fun <reified VM : ViewModel> NavController.sharedHiltViewModel(
    graphRoute: String? = null
): VM {
    val navBackStackEntry: NavBackStackEntry = remember(this) {
        if (graphRoute == null) {
            getBackStackEntry(graph.id) // scope to whole NavHost
        } else {
            getBackStackEntry(graphRoute) // scope to a specific subgraph
        }
    }
    return hiltViewModel(navBackStackEntry)
}
*/

/*
val parentEntry = remember(backStackEntry) {
    navController.getBackStackEntry("DashboardRoot")
}
val dashboardViewModel: DashboardViewModel = hiltViewModel(parentEntry)
*/

/*@Composable
fun rememberSharedViewModel(
    navController: NavHostController,
    viewModelStoreOwner: ViewModelStoreOwner = navController.getViewModelStoreOwner(
        navController.graph.id
    )
): SharedViewModel {
    return viewModel(viewModelStoreOwner = viewModelStoreOwner)
}*/

/*@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.sharedHiltViewModel(
    navController: NavHostController
): VM {
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navController.graph.id)
    }
    return hiltViewModel(parentEntry)
}*/
