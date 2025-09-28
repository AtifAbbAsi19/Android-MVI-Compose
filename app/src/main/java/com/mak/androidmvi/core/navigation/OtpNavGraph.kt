package com.mak.androidmvi.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mak.androidmvi.ui.commonui.OtpScreen
import com.mak.androidmvi.ui.core.RootViewModel

fun NavGraphBuilder.OtpNavGraph(navController: NavHostController, rootViewModel: RootViewModel){

    // Auth subgraph
    navigation<Destination.OTP.Root>(startDestination = Destination.OTP.ValidateOtp) {
        // Splash
        composable<Destination.OTP.ValidateOtp> {
            OtpScreen(
                    onOtpSuccess = { successId ->
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("otp_result", successId)
                        navController.popBackStack()
                }
            )
        }
    }
}

/*
backStackEntry ->
val result = backStackEntry
    .savedStateHandle
    .getStateFlow<String?>("otp_result", null)
    .collectAsState()

HomeScreen(
successId = result.value,
onGoToOtp = {
    navController.navigate(AppDestination.Otp)
}
)*/
