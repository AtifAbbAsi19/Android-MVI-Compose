package com.mak.androidmvi.core.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.ui.Modifier
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
import com.mak.androidmvi.ui.core.AppScaffold
import com.mak.androidmvi.ui.screens.success.SuccessScreen
import com.mak.androidmvi.ui.viewmodel.HomeSharedViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.dashboardNavGraph(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior?
) {

    navigation<Destination.Dashboard.Root>(startDestination = Destination.Dashboard.Home) {

        composable<Destination.Dashboard.Home> {

            val dashboardSharedViewModel =
                it.sharedViewModel<HomeSharedViewModel>(navController = navController)

            /*   AppScaffold(
                   navController = navController,
                   topBar = { scrollBehavior ->
                       scrollBehavior?.let { scrollBehavior ->
                           HomeTopAppBar(
                               userName = "Atif",
                               balance = "$100",
                               profileImageRes = R.drawable.login,
                               scrollBehavior = scrollBehavior
                           )
                       }

                     *//*  LargeTopAppBar(
                            title = { Text("Home") },
                            scrollBehavior = scrollBehavior
                        )*//*
                    },
                    bottomBar = { BottomNavigationBar(navController = navController) },
                    showBottomBar = true
                ) { innerPadding, scrollBehavior ->
*/
            // val safePadding = innerPadding ?: PaddingValues(0.dp)

            HomeScreen(
                modifier = Modifier,
                //   .then(
                //     Modifier.padding(safePadding)
                // ),
                scrollBehavior = scrollBehavior,
                viewModel = dashboardSharedViewModel
            )
            //}
        }

        composable<Destination.Dashboard.Profile> {

            // AppScaffold(navController = navController, showBottomBar = true) { padding, _ ->
            ProfileScreen(
                onUploadPhoto = { navController.navigate(Destination.ProfileSettings.UploadPhoto) },
                onUpdateEmail = { navController.navigate(Destination.ProfileSettings.UpdateEmail) },
                onUpdatePhone = { navController.navigate(Destination.ProfileSettings.UpdatePhoneNumber) },
                successScreen = {
                    navController.navigate(
                        Destination.Success
                    )
                }
            )
            // }
        }


        composable<Destination.Success> {
            SuccessScreen(
            )
        }

        composable<Destination.Dashboard.Settings> {
          SettingsScreen()
        }

        composable<Destination.Dashboard.Chat> {
           ChatScreen()
        }

        composable<Destination.Dashboard.Search> {
            SearchScreen()
        }
    }

}


