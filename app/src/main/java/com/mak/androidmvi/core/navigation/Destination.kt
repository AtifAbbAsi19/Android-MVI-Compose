package com.mak.androidmvi.core.navigation

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import com.mak.androidmvi.core.navigation.Destination.Auth.Signup
import kotlinx.serialization.Serializable


/*// Create a Saver for ScreenState
val ScreenStateSaver: Saver<Destination, List<String>> = listSaver(
    save = { state ->
        when (state) {
            is Destination.Root -> listOf(Destination.Root::class.simpleName)
            is Destination.Splash -> listOf("splash")
            is Destination.Dashboard -> listOf("Dashboard")
        }
    },
    restore = { list ->
        when (list[0]) {
            "Loading" -> Destination.Root
            "Login" -> Destination.Splash
            "Dashboard" -> Destination.Dashboard
            else -> Destination.Dashboard
        }
    }
)*/


sealed interface Destination {

    // -------- Root --------
    @Serializable data object Root : Destination

    @Serializable data object Splash : Destination

    @Serializable data object Success : Destination

    // -------- Auth Graph --------
    sealed interface Auth : Destination {
        @Serializable data object Root : Auth
        @Serializable data object Login : Auth
        @Serializable data object Signup : Auth
        @Serializable data class ForgotPassword(val email: String? = null) : Auth
    }


    // -------- Auth sub-Graph --------
    sealed interface SignupGraph : Auth {
        @Serializable data object Signup : SignupGraph
        @Serializable data class Success(val email: String? = null, val successId : String?= null) : SignupGraph
    }


    // -------- Dashboard Graph --------
    sealed interface Dashboard : Destination {
        @Serializable data object Root : Dashboard
        @Serializable data object Home : Dashboard
        @Serializable data object Profile : Dashboard
        @Serializable data object Chat : Dashboard
        @Serializable data object Search : Dashboard
        @Serializable data object Settings : Dashboard

    }

    // -------- Profile SubGraph --------
    sealed interface ProfileSettings : Destination {
        @Serializable data object Root : ProfileSettings
        @Serializable data object UploadPhoto : ProfileSettings
        @Serializable data object UpdateEmail : ProfileSettings
        @Serializable data object UpdatePhoneNumber : ProfileSettings
    }

    // -------- OTP SubGraph --------
    sealed interface OTP : Destination {
        @Serializable data object Root : Destination
        @Serializable data object ValidateOtp : Destination
    }

}