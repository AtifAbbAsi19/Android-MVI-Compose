package com.mak.androidmvi.ui.core


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallDefaultTopBar(title: String, scrollBehavior: TopAppBarScrollBehavior?) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        scrollBehavior = scrollBehavior
    )
}