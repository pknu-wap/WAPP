package com.wap.designsystem.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designsystem.R

@Composable
fun WappTopBar(
    @StringRes titleRes: Int,
    showLeftButton: Boolean = false,
    showRightButton: Boolean = false,
    onClickLeftButton: () -> Unit = {},
    onClickRightButton: () -> Unit = {},
    @StringRes leftButtonDescriptionRes: Int = R.string.back_button,
    @StringRes rightButtonDescriptionRes: Int = R.string.close_button,
    @DrawableRes leftButtonDrawableRes: Int = R.drawable.ic_back,
    @DrawableRes rightButtonDrawableRes: Int = R.drawable.ic_close,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showLeftButton) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { onClickLeftButton() },
                    painter = painterResource(leftButtonDrawableRes),
                    contentDescription = stringResource(leftButtonDescriptionRes),
                    tint = WappTheme.colors.white,
                )
            }

            Text(
                text = stringResource(titleRes),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )

            if (showRightButton) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { onClickRightButton() },
                    painter = painterResource(rightButtonDrawableRes),
                    contentDescription = stringResource(rightButtonDescriptionRes),
                    tint = WappTheme.colors.white,
                )
            }
        }
    }
}

@Preview("without Button TopBar")
@Composable
fun WappTopBarWithoutButton() {
    WappTheme {
        Surface(
            color = WappTheme.colors.backgroundBlack,
        ) {
            WappTopBar(
                titleRes = R.string.notice,
            )
        }
    }
}

@Preview("with Right Button TopBar")
@Composable
fun WappTopBarWithRightButton() {
    WappTheme {
        Surface(
            color = WappTheme.colors.backgroundBlack,
        ) {
            WappTopBar(
                titleRes = R.string.notice,
                showRightButton = true,
            )
        }
    }
}

@Preview("with Left Button TopBar")
@Composable
fun WappTopBarWithLeftButton() {
    WappTheme {
        Surface(
            color = WappTheme.colors.backgroundBlack,
        ) {
            WappTopBar(
                titleRes = R.string.notice,
                showLeftButton = true,
            )
        }
    }
}

@Preview("with Both Button TopBar")
@Composable
fun WappTopBarWithBothButton() {
    WappTheme {
        Surface(
            color = WappTheme.colors.backgroundBlack,
        ) {
            WappTopBar(
                titleRes = R.string.notice,
                showRightButton = true,
                showLeftButton = true,
            )
        }
    }
}
