package com.mak.androidmvi.core.model

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    var isSelected : Boolean = false,
    var enabled : Boolean = true,
    val onClick: () -> Unit ={},
)