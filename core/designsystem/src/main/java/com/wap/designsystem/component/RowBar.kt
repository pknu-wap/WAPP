package com.wap.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designsystem.R

@Composable
fun WappRowBar(
    title: String,
    onBarClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(horizontal = 20.dp)
            .clickable { onBarClicked() },
    ) {
        Text(
            text = title,
            style = WappTheme.typography.contentRegular,
            color = WappTheme.colors.white,
            modifier = Modifier
                .align(Alignment.CenterStart),
        )
        Image(
            painter = painterResource(id = R.drawable.ic_right_arrow_yellow),
            contentDescription = stringResource(id = R.string.right_yellow_arrow_description),
            modifier = Modifier
                .align(Alignment.CenterEnd),
        )
    }
}

@Preview
@Composable
fun PreviewWappRowBar() {
    WappRowBar(title = "알림 설정")
}
