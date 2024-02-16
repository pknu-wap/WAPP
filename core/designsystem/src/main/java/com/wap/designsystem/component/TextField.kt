package com.wap.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme

@Composable
fun WappTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    @StringRes label: Int,
    isError: Boolean = false,
    supportingText: String = "",
) {
    TextField(
        value = value,
        onValueChange = { value -> onValueChanged(value) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = WappTheme.colors.black25,
            focusedContainerColor = WappTheme.colors.black25,
            errorContainerColor = WappTheme.colors.black25,
            unfocusedLabelColor = WappTheme.colors.white,
            focusedLabelColor = WappTheme.colors.white,
            unfocusedTextColor = WappTheme.colors.white,
            focusedTextColor = WappTheme.colors.white,
            errorTextColor = WappTheme.colors.white,
            unfocusedSupportingTextColor = WappTheme.colors.white,
            focusedSupportingTextColor = WappTheme.colors.white,
            focusedIndicatorColor = WappTheme.colors.yellow34,
            cursorColor = WappTheme.colors.yellow34,
        ),
        label = {
            Text(text = stringResource(label))
        },
        isError = isError,
        supportingText = {
            Text(text = supportingText)
        },
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WappRoundedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    @StringRes placeholder: Int,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedTextColor = WappTheme.colors.white,
            unfocusedTextColor = WappTheme.colors.white,
            focusedContainerColor = WappTheme.colors.black25,
            unfocusedContainerColor = WappTheme.colors.black25,
            focusedIndicatorColor = WappTheme.colors.black25,
            unfocusedIndicatorColor = WappTheme.colors.black25,
            cursorColor = WappTheme.colors.yellow34,
        ),
        placeholder = {
            androidx.compose.material.Text(
                text = stringResource(id = placeholder),
                color = WappTheme.colors.gray82,
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        shape = RoundedCornerShape(10.dp),
    )
}
