package com.mak.androidmvi.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
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
import com.mak.androidmvi.ui.core.BottomNavigationBar
import com.mak.androidmvi.ui.core.DashboardScaffold
import com.mak.androidmvi.ui.screens.success.SuccessScreen
import com.mak.androidmvi.ui.viewmodel.HomeSharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.dashboardNavGraph(navController: NavHostController){



        navigation<Destination.Dashboard.Root>(startDestination = Destination.Dashboard.Home) {


            composable<Destination.Dashboard.Home> {

                val viewModel = it.sharedViewModel<HomeSharedViewModel>( navController = navController)


                DashboardScaffold(
                    navController = navController,
                    topBar = { scrollBehavior ->
                        LargeTopAppBar(
                            title = { Text("Home") },
                            scrollBehavior = scrollBehavior
                        )
                    },
                ) { innerPadding, scrollBehavior ->

                    val safePadding = innerPadding ?: PaddingValues(0.dp)

                    HomeScreen(
                            modifier = Modifier
                                .then(
                                    Modifier.padding(safePadding)
                                ),
                            scrollBehavior = scrollBehavior,
                            viewModel = viewModel
                        )

                }
            }

            composable<Destination.Dashboard.Profile> {

                var showBottomNavigation by rememberSaveable { mutableStateOf(true) }

                DashboardScaffold(navController = navController, showBottomBar = showBottomNavigation) {padding, _ ->
                    ProfileScreen(
                        onUploadPhoto = { navController.navigate(Destination.ProfileSettings.UploadPhoto) },
                        onUpdateEmail = {  navController.navigate(Destination.ProfileSettings.UpdateEmail)  },
                        onUpdatePhone = {  navController.navigate(Destination.ProfileSettings.UpdatePhoneNumber) },
                        successScreen = {
                            showBottomNavigation = false

                            navController.navigate(
                                Destination.Success
                            )
                        }
                    )
                }
            }


            composable<Destination.Success> {
                DashboardScaffold(navController) {padding, _ -> SuccessScreen(
                ) }
            }

            composable<Destination.Dashboard.Settings> {
                DashboardScaffold(navController) {padding, _ -> SettingsScreen() }
            }

            composable<Destination.Dashboard.Chat> {
                DashboardScaffold(navController) { padding, _ -> ChatScreen() }
            }

            composable<Destination.Dashboard.Search> {
                DashboardScaffold(navController) { padding, _ -> SearchScreen() }
            }
    }

}


