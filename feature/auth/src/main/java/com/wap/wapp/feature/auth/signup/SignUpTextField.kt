package com.wap.wapp.feature.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme

@Composable
internal fun SignUpTextField(
    iconDescription: String,
    title: String,
    text: String,
    onValueChanged: (String) -> Unit,
    hint: String,
    supportingText: String,
    icon: Int,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = iconDescription,
                tint = WappTheme.colors.white,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                color = WappTheme.colors.white,
                style = WappTheme.typography.contentBold,
            )
        }
        TextField(
            value = text,
            onValueChange = onValueChanged,
            modifier = modifier,
            colors = TextFieldDefaults.textFieldColors(
                textColor = WappTheme.colors.white,
                backgroundColor = WappTheme.colors.backgroundBlack,
                focusedIndicatorColor = WappTheme.colors.white,
                unfocusedIndicatorColor = WappTheme.colors.white,
            ),
            maxLines = 1,
            placeholder = {
                Text(text = hint, color = WappTheme.colors.grayA2)
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = supportingText,
            color = WappTheme.colors.yellow34,
            style = WappTheme.typography.captionRegular,
        )
    }
}
