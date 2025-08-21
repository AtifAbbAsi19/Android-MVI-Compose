package com.mak.androidmvi.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onSignup: () -> Unit, onForgotPassword: () -> Unit) {



    // Your UI for Splash
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column {
            Text(
                modifier = Modifier.clickable{
                    onLoginSuccess.invoke()
                },
                text = "Login",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                modifier = Modifier.clickable{
                    onSignup.invoke()
                },
                text = "Signup",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )


            Text(
                modifier = Modifier.clickable{
                    onForgotPassword.invoke()
                },
                text = "Forgot Password",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )


        }


    }

}