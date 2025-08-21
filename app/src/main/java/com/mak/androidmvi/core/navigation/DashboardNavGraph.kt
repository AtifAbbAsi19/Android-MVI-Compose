package com.mak.androidmvi.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mak.androidmvi.core.sharedViewModel
import com.mak.androidmvi.ui.HomeScreen
import com.mak.androidmvi.ui.ProfileScreen
import com.mak.androidmvi.ui.SettingsScreen
import com.mak.androidmvi.ui.core.DashboardScaffold
import com.mak.androidmvi.ui.viewmodel.HomeSharedViewModel

fun NavGraphBuilder.dashboardNavGraph(navController: NavHostController){

        navigation<Destination.Dashboard.Root>(startDestination = Destination.Dashboard.Home) {
            composable<Destination.Dashboard.Home> {

                val viewMode = it.sharedViewModel<HomeSharedViewModel>( navController = navController)

                DashboardScaffold(navController) { HomeScreen() }
            }

            composable<Destination.Dashboard.Profile> {
                DashboardScaffold(navController) {
                    ProfileScreen(
                        onUploadPhoto = { /*navController.navigate(UploadPhoto)*/ },
                        onUpdateEmail = { /*navController.navigate(UpdateEmail)*/ },
                        onUpdatePhone = { /*navController.navigate(UpdatePhoneNumber)*/ }
                    )
                }
            }

            composable<Destination.Dashboard.Settings> {
                DashboardScaffold(navController) { SettingsScreen() }
            }

          /*  // Profile subgraph
            composable<UploadPhoto> { UploadPhotoScreen(onBack = { navController.popBackStack() }) }
            composable<UpdateEmail> { UpdateEmailScreen(onBack = { navController.popBackStack() }) }
            composable<UpdatePhoneNumber> { UpdatePhoneScreen(onBack = { navController.popBackStack() }) }*/
    }

}


