package com.mak.androidmvi.ui.screens.signup

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mak.androidmvi.ui.viewmodel.auth.AuthSharedViewModel


@Composable
fun SignupSuccess(successId: String, onLogin: () -> Unit, authSharedViewModel: AuthSharedViewModel) {


    val state by authSharedViewModel.uiState.collectAsStateWithLifecycle()

    Log.d("appLog",state.pageInfo)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("✅ Success!")
        Spacer(Modifier.height(8.dp))
        Text("Your Success ID: $successId")

        Spacer(Modifier.height(16.dp))
        Button(onClick = onLogin) {
            Text("Back to Home")
        }
    }
}