package com.wap.wapp.feature.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
fun SignUpTextField(
    iconDescription: String,
    fieldName: String,
    fieldHint: String,
    fieldSupportingText: String,
    fieldIcon: Int,
) {
    var dummyText = ""
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = fieldIcon),
                contentDescription = iconDescription,
                tint = WappTheme.colors.white,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = fieldName,
                color = WappTheme.colors.white,
                style = WappTheme.typography.contentBold,
            )
        }
        TextField(
            value = dummyText,
            onValueChange = { dummyText = it },
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = WappTheme.colors.white,
                backgroundColor = WappTheme.colors.backgroundBlack,
                focusedIndicatorColor = WappTheme.colors.white,
                unfocusedIndicatorColor = WappTheme.colors.white,
            ),
            placeholder = {
                Text(
                    text = fieldHint,
                    color = WappTheme.colors.gray1,
                )
            },
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = fieldSupportingText,
            color = WappTheme.colors.yellow,
            style = WappTheme.typography.captionRegular,
        )
    }
}
