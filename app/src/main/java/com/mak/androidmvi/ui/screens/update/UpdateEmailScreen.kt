package com.mak.androidmvi.ui.screens.update

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun UpdateEmailScreen(onBack: () -> Boolean) {



    Column {
        Text(
            modifier = Modifier.clickable{

            },
            text = "Update Email",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

    }


}