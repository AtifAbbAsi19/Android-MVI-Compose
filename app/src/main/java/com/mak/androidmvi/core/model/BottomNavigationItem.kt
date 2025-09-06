package com.mak.androidmvi.core.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector
import com.mak.androidmvi.core.navigation.Destination

@Stable
data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val route: Destination,
    val badgeCount: Int? = null,
    var isSelected: Boolean = false,
    var enabled: Boolean = true,
    val onClick: () -> Unit = {},
    val mainRoute: Destination? = null,
)