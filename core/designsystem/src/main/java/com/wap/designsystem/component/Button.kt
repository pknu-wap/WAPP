package com.wap.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designsystem.R

@Composable
fun WappButton(
    modifier: Modifier = Modifier,
    @StringRes textRes: Int = R.string.done,
    onClick: () -> Unit,
    isEnabled: Boolean = true,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        onClick = { onClick() },
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            contentColor = WappTheme.colors.white,
            containerColor = WappTheme.colors.yellow34,
            disabledContentColor = WappTheme.colors.white,
            disabledContainerColor = WappTheme.colors.grayA2,
        ),
        shape = RoundedCornerShape(10.dp),
        content = {
            Text(
                text = stringResource(textRes),
                style = WappTheme.typography.contentRegular,
            )
        },
    )
}
