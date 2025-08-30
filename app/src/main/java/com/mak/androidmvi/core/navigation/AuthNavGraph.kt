package com.mak.androidmvi.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.mak.androidmvi.ui.ForgotPasswordScreen
import com.mak.androidmvi.ui.screens.login.LoginScreen
import com.mak.androidmvi.ui.SignupScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController){

    // Auth subgraph
    navigation<Destination.Auth.Root>(startDestination = Destination.Auth.Login) {
        composable<Destination.Auth.Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Destination.Dashboard.Root) {
                        popUpTo(Destination.Auth.Root) { inclusive = true }
                    }
                },
                onSignup = { navController.navigate(Destination.Auth.Signup) },
                onForgotPassword = { navController.navigate(Destination.Auth.ForgotPassword()) }
            )
        }

        composable<Destination.Auth.Signup> {
            SignupScreen(onBack = { navController.popBackStack() })
        }

        composable<Destination.Auth.ForgotPassword> { entry ->
            val args = entry.toRoute<Destination.Auth.ForgotPassword>()
            ForgotPasswordScreen(
                emailPrefill = args.email,
                onBack = { navController.popBackStack() }
            )
        }
    }
}