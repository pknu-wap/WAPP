package com.wap.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designsystem.R

@Composable
fun WappMainTopBar(
    @StringRes titleRes: Int,
    @StringRes contentRes: Int,
    showSettingButton: Boolean = false,
    onClickSettingButton: () -> Unit = {},
    @StringRes settingButtonDescriptionRes: Int = R.string.setting_button,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .padding(top = 40.dp, start = 24.dp, end = 24.dp, bottom = 20.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = titleRes),
                color = WappTheme.colors.white,
                style = WappTheme.typography.titleBold.copy(fontSize = 24.sp),
                modifier = Modifier.align(Alignment.CenterStart),
            )

            if (showSettingButton) {
                Image(
                    painter =
                    painterResource(id = R.drawable.ic_subtract),
                    contentDescription = stringResource(id = settingButtonDescriptionRes),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { onClickSettingButton() },
                )
            }
        }
        Text(
            text = stringResource(id = contentRes),
            color = WappTheme.colors.white,
            style = WappTheme.typography.contentRegular,
        )
    }
}

@Preview("without Button TopBar")
@Composable
fun WappMainTopBarWithoutButton() {
    WappTheme {
        Surface(
            color = WappTheme.colors.backgroundBlack,
        ) {
            WappMainTopBar(
                titleRes = R.string.notice,
                contentRes = R.string.notice,
            )
        }
    }
}

@Preview("with Button TopBar")
@Composable
fun WappMainTopBarWithButton() {
    WappTheme {
        Surface(
            color = WappTheme.colors.backgroundBlack,
        ) {
            WappMainTopBar(
                titleRes = R.string.notice,
                contentRes = R.string.notice,
                showSettingButton = true,
                onClickSettingButton = {},
            )
        }
    }
}
