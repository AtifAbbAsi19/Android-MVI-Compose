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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.mak.androidmvi.ui.screens.login.LoginIntent



@Stable
data class InputFieldBuilder(
    val label : LabelBuilder?= null,
    var value : String,
    val hint : String?= null,
    var footerMessage : String?= null,
    var isError : Boolean= false,
    val onValueChange: (String) -> Unit,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
){

    @Immutable
    data class LabelBuilder(
        val label : String?= null,
    )
}


@Composable
fun InputField(
    modifier : Modifier,
    builder : InputFieldBuilder){

    Column {

         LabelView(builder.label)

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

@Composable
fun LabelView(label: InputFieldBuilder.LabelBuilder?) {
    label?.let {
        Text(it.label?:"")
    }
}