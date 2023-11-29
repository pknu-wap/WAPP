package com.wap.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
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
