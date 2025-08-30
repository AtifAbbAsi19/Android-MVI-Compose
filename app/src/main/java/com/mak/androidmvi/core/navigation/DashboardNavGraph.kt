package com.mak.androidmvi.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mak.androidmvi.core.sharedViewModel
import com.mak.androidmvi.ui.ChatScreen
import com.mak.androidmvi.ui.HomeScreen
import com.mak.androidmvi.ui.ProfileScreen
import com.mak.androidmvi.ui.SearchScreen
import com.mak.androidmvi.ui.SettingsScreen
import com.mak.androidmvi.ui.core.DashboardScaffold
import com.mak.androidmvi.ui.viewmodel.AppSharedViewModel
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
                        onUploadPhoto = { navController.navigate(Destination.ProfileSettings.UploadPhoto) },
                        onUpdateEmail = {  navController.navigate(Destination.ProfileSettings.UpdateEmail)  },
                        onUpdatePhone = {  navController.navigate(Destination.ProfileSettings.UpdatePhoneNumber) }
                    )
                }
            }

            composable<Destination.Dashboard.Settings> {
                DashboardScaffold(navController) { SettingsScreen() }
            }

            composable<Destination.Dashboard.Chat> {
                DashboardScaffold(navController) { ChatScreen() }
            }

            composable<Destination.Dashboard.Search> {
                DashboardScaffold(navController) { SearchScreen() }
            }
    }

}


