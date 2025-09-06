package com.mak.androidmvi.ui.screens.login

import androidx.compose.runtime.Stable
import com.mak.androidmvi.R
import com.mak.androidmvi.designsystem.FiledState
import com.mak.androidmvi.ui.extensions.isValidEmail
import com.mak.androidmvi.ui.extensions.isValidPassword

// Represents the UI state
@Stable
data class LoginUiState(
    val emailLeadingIcon: Int = R.drawable.sharp_delivery_truck_speed_24,
    val emailTrailingIcon: Int = R.drawable.sharp_delivery_truck_speed_24,
    val email: String = "",
    val emailFiledState :FiledState  =   FiledState.Default,
    val password: String = "",
    val reconfirmPassword: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
){
    val emailLabel = "Email"
    val emailHint = "Enter Email"
    val passwordLabel = "Password"
    val passwordHint = "Enter Password"
    val reConfirmPasswordLabel = "Reconfirm Password"

    val isLoginEnabled: Boolean
        get() = email.isNotBlank() && email.isValidEmail() &&
                password.isNotBlank() &&  password.isValidPassword() &&
                reconfirmPassword.isNotBlank() &&  reconfirmPassword.isValidPassword() &&
                !isLoading
}