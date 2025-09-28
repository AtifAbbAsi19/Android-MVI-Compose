package com.mak.androidmvi.core.navigation

import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.mak.androidmvi.ui.core.RootViewModel
import com.mak.androidmvi.ui.screens.forgotpassword.ForgotPasswordScreen
import com.mak.androidmvi.ui.screens.login.LoginScreen
import com.mak.androidmvi.ui.screens.login.LoginViewModel
import com.mak.androidmvi.ui.viewmodel.SharedAuthViewModel

fun NavGraphBuilder.authNavGraph(navController: NavHostController, rootViewModel: RootViewModel) {

    // Auth subgraph
    navigation<Destination.Auth.Root>(startDestination = Destination.Auth.Login) {
        composable<Destination.Auth.Login> { backStackEntry ->

            /* val route = Destination.Auth.Root::class.simpleName
             println(route) // "Root"*/

            val route = Destination.Auth.Root::class.qualifiedName ?: ""
            println(route) // "com.package.Destination.Auth.Root"


            // val sharedViewModel: SharedAuthViewModel = viewModel(parentEntry)

            //  Scope the SharedAuthViewModel to the Auth.Root graph
            val appParentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(route)
            }

            val appSharedViewModel: SharedAuthViewModel =
                viewModel(appParentEntry)   //hiltViewModel(parentEntry)


             val sharedAuthViewModel: SharedAuthViewModel =   viewModel(appParentEntry)   //hiltViewModel(parentEntry)

            val loginViewModel: LoginViewModel = viewModel(backStackEntry)

            LoginScreen(
                rootViewModel = rootViewModel,
                viewModel = loginViewModel,
                sharedAuthViewModel = sharedAuthViewModel,
                onLoginSuccess = {
                    navController.navigate(Destination.Dashboard.Root) {
                        popUpTo(Destination.Auth.Root) { inclusive = true }
                    }
                },
                onSignup = { navController.navigate(Destination.Auth.Signup) },
                onForgotPassword = { navController.navigate(Destination.Auth.ForgotPassword()) },
            )
        }


        composable<Destination.Auth.ForgotPassword> { backStackEntry ->
            val args = backStackEntry.toRoute<Destination.Auth.ForgotPassword>()


            //  Scope the SharedAuthViewModel to the Auth.Root graph
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Destination.Auth.Root)
            }

            val sharedAuthViewModel: SharedAuthViewModel =
                viewModel(parentEntry)   //hiltViewModel(parentEntry)


            ForgotPasswordScreen(
                rootViewModel = rootViewModel,
                sharedAuthViewModel = sharedAuthViewModel,
                email = args.email,
                onBack = { navController.popBackStack() }
            )
        }


        //Sub Nav graph
        signupGraph(navController)
    }
}