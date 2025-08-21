package com.mak.androidmvi.core.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {

    // -------- Root --------
    @Serializable data object Root : Destination

    @Serializable data object Splash : Destination

    // -------- Auth Graph --------
    sealed interface Auth : Destination {
        @Serializable data object Root : Auth
        @Serializable data object Login : Auth
        @Serializable data object Signup : Auth
        @Serializable data class ForgotPassword(val email: String? = null) : Auth
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
    sealed interface ProfileSettings : Dashboard {
        @Serializable data object Root : ProfileSettings
        @Serializable data object UploadPhoto : ProfileSettings
        @Serializable data object UpdateEmail : ProfileSettings
        @Serializable data object UpdatePhoneNumber : ProfileSettings
    }
}