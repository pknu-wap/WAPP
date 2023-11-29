package com.wap.designsystem.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
    modifier: Modifier = Modifier,
    showLeftButton: Boolean = false,
    showRightButton: Boolean = false,
    onClickLeftButton: () -> Unit = {},
    onClickRightButton: () -> Unit = {},
    @StringRes leftButtonDescriptionRes: Int = R.string.back_button,
    @StringRes rightButtonDescriptionRes: Int = R.string.close_button,
    @DrawableRes leftButtonDrawableRes: Int = R.drawable.ic_back,
    @DrawableRes rightButtonDrawableRes: Int = R.drawable.ic_close,
) {
    Box(
        modifier = modifier,
    ) {
        if (showLeftButton) {
            Icon(
                painter = painterResource(leftButtonDrawableRes),
                contentDescription = stringResource(leftButtonDescriptionRes),
                tint = WappTheme.colors.white,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onClickLeftButton() }
                    .align(Alignment.CenterStart),
            )
        }

        Text(
            text = stringResource(titleRes),
            textAlign = TextAlign.Center,
            style = WappTheme.typography.titleBold,
            color = WappTheme.colors.white,
            modifier = Modifier.align(Alignment.Center),
        )

        if (showRightButton) {
            Icon(
                painter = painterResource(rightButtonDrawableRes),
                contentDescription = stringResource(rightButtonDescriptionRes),
                tint = WappTheme.colors.white,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onClickRightButton() }
                    .align(Alignment.CenterEnd),
            )
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                titleRes = R.string.notice,
                showRightButton = true,
                showLeftButton = true,
            )
        }
    }
}
