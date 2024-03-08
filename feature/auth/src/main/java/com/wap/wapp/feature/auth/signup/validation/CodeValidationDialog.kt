package com.wap.wapp.feature.auth.signup.validation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton
import com.wap.designsystem.component.WappTextField
import com.wap.designsystem.modifier.addFocusCleaner
import com.wap.wapp.feature.auth.R

@Composable
internal fun CodeValidationDialog(
    code: String,
    isError: Boolean,
    supportingText: String,
    setValidationCode: (String) -> Unit,
    onConfirmRequest: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 20.dp)
                .addFocusCleaner(focusManager)
                .clip(RoundedCornerShape(10.dp))
                .background(WappTheme.colors.black25),
        ) {
            Text(
                text = stringResource(R.string.sign_up_dialog_title),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 20.dp),
            )

            Text(
                text = stringResource(R.string.sign_up_dialog_content),
                style = WappTheme.typography.captionMedium,
                color = WappTheme.colors.white,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 10.dp),
            )

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            WappTextField(
                value = code,
                onValueChanged = setValidationCode,
                label = R.string.code,
                isError = isError,
                supportingText = supportingText,
            )

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            WappButton(
                onClick = onConfirmRequest,
                isEnabled = code.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
            )
        }
    }
}
