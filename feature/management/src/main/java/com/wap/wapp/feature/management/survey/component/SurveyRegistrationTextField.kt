package com.wap.wapp.feature.management.survey.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme

@Composable
internal fun SurveyRegistrationTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    placeholder: String,
) {
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
            cursorColor = WappTheme.colors.yellow,
        ),
        placeholder = {
            Text(
                text = placeholder,
            )
        },
        shape = RoundedCornerShape(10.dp),
    )
}
