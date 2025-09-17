package com.mak.androidmvi.core.navigation

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.mak.androidmvi.ui.SignupScreen
import com.mak.androidmvi.ui.SignupSuccess
import com.mak.androidmvi.ui.extensions.safeEmpty


fun NavGraphBuilder.signupGraph(navController: NavHostController){

    // Auth subgraph
    navigation<Destination.Auth.Signup>(startDestination = Destination.SignupGraph.Signup) {

        composable<Destination.SignupGraph.Signup> { backStackEntry ->
            val result = backStackEntry
                .savedStateHandle
                .getStateFlow<String?>("otp_result", null)
                .collectAsState()

            SignupScreen(
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

            SignupSuccess(
                successId = args.successId.safeEmpty(),
                onLogin = {
                    navController.navigate(Destination.Auth.Root)
                }
            )
        }

    }
}
