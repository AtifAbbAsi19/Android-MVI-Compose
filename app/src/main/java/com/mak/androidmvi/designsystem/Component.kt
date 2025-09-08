package com.mak.androidmvi.designsystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mak.androidmvi.ui.screens.login.LoginIntent

@Immutable
sealed class FiledState {
    object Default : FiledState()
    object Error : FiledState()
    object Warning : FiledState()
}

@Immutable
data class InputFieldBuilder(
    val filedState: FiledState,
    val leadingIcon: PainterHolder? = null,
    val label: InputFieldBuilder.LabelBuilder?,
    val trailing: PainterHolder? = null,
    val value: String,
    val hint: String? = null,
    val footerMessage: String? = null,
    val isError: Boolean = false,
    val onValueChange: (String) -> Unit,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
) {

    @Immutable
    data class LabelBuilder(
        val label: String? = null,
    )
}


@Composable
fun InputField(
    modifier: Modifier,
    builder: InputFieldBuilder
) {

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        RowView(
            leadingIcon = builder.leadingIcon,
            label = builder.label,
            trailing = builder.trailing
        )
        // LabelView(builder.label)

        // Email
        OutlinedTextField(
            value = builder.value,
            onValueChange = builder.onValueChange,
            label = null,
            placeholder = { Text(text = builder.hint ?: "") },
            isError = builder.isError,
            keyboardOptions = builder.keyboardOptions,
            modifier = Modifier.fillMaxWidth()
        )

        builder.footerMessage?.let {
            Text(
                it, color = if (builder.isError) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                }
            )
        }

    }

}

@Composable
fun RowView(
    leadingIcon: PainterHolder? = null,
    label: InputFieldBuilder.LabelBuilder?,
    trailing: PainterHolder? = null,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        leadingIcon?.let {
            IconView(IconViewBuilder(leadingIcon.painter))
        }

        label?.let {
            LabelView(it)
        }

        trailing?.let {
            IconView(IconViewBuilder(trailing.painter))
        }

    }

}

@Composable
fun LabelView(label: InputFieldBuilder.LabelBuilder?) {
    label?.let {
        Text(it.label ?: "")
    }
}


@Immutable
data class IconViewBuilder(
    val icon: Painter,
    val contentDescription: String? = null,
)


@Composable
fun IconView(iconViewBuilder: IconViewBuilder) {
    Icon(
        painter = iconViewBuilder.icon,
        contentDescription = iconViewBuilder.contentDescription
    )
}
