package com.mak.androidmvi.core.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.mak.androidmvi.ui.screens.signup.SignupScreen
import com.mak.androidmvi.ui.screens.signup.SignupSuccess
import com.mak.androidmvi.ui.extensions.safeEmpty
import com.mak.androidmvi.ui.viewmodel.SharedAuthViewModel


fun NavGraphBuilder.signupGraph(navController: NavHostController){

    // Auth subgraph
    navigation<Destination.Auth.Signup>(startDestination = Destination.SignupGraph.Signup) {

        composable<Destination.SignupGraph.Signup> { backStackEntry ->
            val result = backStackEntry
                .savedStateHandle
                .getStateFlow<String?>("otp_result", null)
                .collectAsState()

            //  Scope the SharedAuthViewModel to the Auth.Root graph
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Destination.Auth.Root)
            }

            val sharedAuthViewModel: SharedAuthViewModel =   viewModel(parentEntry)   //hiltViewModel(parentEntry)


            SignupScreen(
                sharedAuthViewModel = sharedAuthViewModel,
                successId = result.value,
                onBack = { navController.popBackStack() },
                onGoToOtp = {
                    navController.navigate(Destination.OTP.Root)
                },
                onConfirmationScreen = {
                    navController.navigate(Destination.SignupGraph.Success(successId = result.value))
                }
            )
        }

        composable<Destination.SignupGraph.Success> { backStackEntry ->

            val args = backStackEntry.toRoute<Destination.SignupGraph.Success>()

            //  Scope the SharedAuthViewModel to the Auth.Root graph
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Destination.Auth.Root)
            }

            val sharedAuthViewModel: SharedAuthViewModel =   viewModel(parentEntry)   //hiltViewModel(parentEntry)



            SignupSuccess(
                sharedAuthViewModel = sharedAuthViewModel,
                successId = args.successId.safeEmpty(),
                onLogin = {
                    navController.navigate(Destination.Auth.Root)
                }
            )
        }

    }
}
