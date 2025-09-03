package com.mak.androidmvi.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.mak.androidmvi.ui.screens.login.LoginIntent



@Stable
data class InputFieldBuilder(
    var label : String?= null,
    var value : String,
    var hint : String?= null,
    var footerMessage : String?= null,
    var isError : Boolean= false,
    val onValueChange: (String) -> Unit,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
)


@Composable
fun InputField(builder : InputFieldBuilder){

    Column {

        builder.label?.let {
            Text(it)
        }

        // Email
        OutlinedTextField(
            value = builder.value,
            onValueChange = builder.onValueChange,
            label = null,
            placeholder = { Text(text = builder.hint?:"") },
            isError = builder.isError,
            keyboardOptions = builder.keyboardOptions,
            modifier = Modifier.fillMaxWidth()
        )

        builder.footerMessage?.let {
            Text(it, color = if(builder.isError){
                MaterialTheme.colorScheme.error
            }else{
                MaterialTheme.colorScheme.primary
            }
                )
        }

    }

}