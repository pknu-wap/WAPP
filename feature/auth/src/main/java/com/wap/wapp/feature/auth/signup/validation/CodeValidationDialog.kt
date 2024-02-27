package com.wap.wapp.feature.auth.signup.validation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton
import com.wap.designsystem.component.WappTextField
import com.wap.wapp.feature.auth.R

@Composable
internal fun CodeValidationDialog(
    code: String,
    setValidationCode: (String) -> Unit,
    onConfirmRequest: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(shape = RoundedCornerShape(10.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = WappTheme.colors.black25)
                    .padding(10.dp),
            ) {
                Text(
                    text = stringResource(R.string.sign_up_dialog_title),
                    style = WappTheme.typography.titleBold,
                    color = WappTheme.colors.white,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = stringResource(R.string.sign_up_dialog_content),
                    style = WappTheme.typography.captionMedium,
                    color = WappTheme.colors.white,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                WappTextField(
                    value = code,
                    onValueChanged = setValidationCode,
                    label = R.string.code,
                    isError = false,
                    supportingText = "",
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                WappButton(
                    onClick = onConfirmRequest,
                    isEnabled = code.isNotBlank(),
                    modifier = Modifier.padding(horizontal = 32.dp),
                )
            }
        }
    }
}
