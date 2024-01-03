package com.wap.wapp.feature.management.registration.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme

@Composable
internal fun RegistrationTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textAlign: TextAlign? = null,
    placeholder: String,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(10.dp),
        textStyle = WappTheme.typography.contentMedium.copy(
            textAlign = textAlign,
        ),
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
            Text(
                text = placeholder,
                color = WappTheme.colors.gray82,
                style = WappTheme.typography.contentMedium.copy(fontSize = 15.sp),
            )
        },
        modifier = modifier,
    )
}
