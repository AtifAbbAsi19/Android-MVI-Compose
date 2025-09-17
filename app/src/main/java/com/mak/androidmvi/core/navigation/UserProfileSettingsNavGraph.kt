package com.mak.androidmvi.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mak.androidmvi.ui.UpdateEmailScreen
import com.mak.androidmvi.ui.UpdatePhoneScreen
import com.mak.androidmvi.ui.UploadPhotoScreen

fun NavGraphBuilder.userProfileSettingsNavGraph(navController: NavHostController){

    navigation<Destination.ProfileSettings.Root>(startDestination = Destination.ProfileSettings.UpdateEmail) {

        composable<Destination.ProfileSettings.UpdateEmail> { UpdateEmailScreen(onBack = { navController.popBackStack() }) }

        composable<Destination.ProfileSettings.UploadPhoto> { UploadPhotoScreen(onBack = { navController.popBackStack() }) }

        composable<Destination.ProfileSettings.UpdatePhoneNumber> { UpdatePhoneScreen(onBack = { navController.popBackStack() }) }
    }

}