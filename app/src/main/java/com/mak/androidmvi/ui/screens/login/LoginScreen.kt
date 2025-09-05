package com.mak.androidmvi.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mak.androidmvi.R
import com.mak.androidmvi.core.asPainter
import com.mak.androidmvi.designsystem.IconViewBuilder
import com.mak.androidmvi.designsystem.InputField
import com.mak.androidmvi.designsystem.InputFieldBuilder

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit, onSignup: () -> Unit, onForgotPassword: () -> Unit) {

   //first approach
    val effect = viewModel.effect.collectAsStateWithLifecycle( initialValue = Unit)

    //second apprach
    val state by viewModel.state

    val mutableStateFlow by viewModel.stateFlow.collectAsStateWithLifecycle()

    // Make column scrollable
    val scrollState = rememberScrollState()

    val emailPainter = painterResource(R.drawable.sharp_delivery_truck_speed_24)

    // Now wrap the builder itself in remember (not the painterResource call)
    val leadingEmailIcon = remember(emailPainter) {
        IconViewBuilder(
            icon = emailPainter,
            contentDescription = "Email Icon"
        )
    }

    val trailingEmailIcon = remember(emailPainter) {
        IconViewBuilder(
            icon = emailPainter,
            contentDescription = "Trailing Icon"
        )
    }

    // Handle effects
    LaunchedEffect(effect.value) {
        when (effect.value) {
            is LoginEffect.NavigateHome -> onLoginSuccess()
            is LoginEffect.NavigateSignup -> onSignup()
            is LoginEffect.NavigateForgotPassword -> onForgotPassword()
            null -> {}
        }
    }

    var emailFocused by rememberSaveable { mutableStateOf(false) }
    var passwordFocused by rememberSaveable { mutableStateOf(false) }


    // Your UI for Login
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState) // enable scrolling
                .padding(24.dp)
                .imePadding(), // adjusts padding when keyboard appears
            verticalArrangement = Arrangement.spacedBy(space = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            //to avoid rendering
            Logo()



            InputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focus ->
                        if (emailFocused && !focus.isFocused) viewModel.validateEmailOnFocusLost()
                        emailFocused = focus.isFocused
                    },
                builder = InputFieldBuilder(
                    leadingIcon = leadingEmailIcon,
                    label = InputFieldBuilder.LabelBuilder(
                        label =  mutableStateFlow.emailLabel
                    ),
                    trailing = trailingEmailIcon,
                    hint = mutableStateFlow.emailHint,
                    value = mutableStateFlow.email,
                    onValueChange = { viewModel.onIntent(LoginIntent.EnterEmail(it)) },
                    isError = mutableStateFlow.emailError != null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )
            )

            Spacer(Modifier.height(12.dp))

            // Password

            InputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focus ->
                        if (passwordFocused && !focus.isFocused) viewModel.validatePasswordOnFocusLost()
                        passwordFocused = focus.isFocused
                    },
                builder = InputFieldBuilder(
                    label = InputFieldBuilder.LabelBuilder(
                        label =  mutableStateFlow.passwordLabel
                    ),
                    hint = mutableStateFlow.passwordHint,
                    value = mutableStateFlow.password,
                    onValueChange = { viewModel.onIntent(LoginIntent.EnterPassword(it)) },
                    isError = mutableStateFlow.passwordError != null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    )
                )
            )

            OutlinedTextField(
                value = mutableStateFlow.password,
                onValueChange = { viewModel.onIntent(LoginIntent.EnterPassword(it)) },
                label = { Text(mutableStateFlow.reConfirmPasswordLabel) },
                placeholder = { Text("password") },
                isError = mutableStateFlow.passwordError != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focus ->
                        if (passwordFocused && !focus.isFocused) viewModel.validatePasswordOnFocusLost()
                        passwordFocused = focus.isFocused
                    }
            )
            if (mutableStateFlow.passwordError != null) {
                Text(mutableStateFlow.passwordError?:"", color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { viewModel.onIntent(LoginIntent.SubmitLogin) },
                modifier = Modifier.fillMaxWidth(),
                enabled = mutableStateFlow.isLoginEnabled
            ) {
                Text(if (mutableStateFlow.isLoading) "Logging in..." else "Login")
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Forgot password?",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { viewModel.onIntent(LoginIntent.NavigateToForgotPassword) }
            )

            Spacer(Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Don’t have an account?")
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Sign Up",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { viewModel.onIntent(LoginIntent.NavigateToSignup) }
                )
            }
        }
    }
}

@Composable
fun Logo(){
    val logo = remember { R.drawable.login }

    // Logo
    Image(
        painter = logo.asPainter(),
        contentDescription = "splash_logo"
    )
}