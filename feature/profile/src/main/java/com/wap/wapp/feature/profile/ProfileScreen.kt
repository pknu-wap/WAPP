package com.wap.wapp.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme

@Composable
internal fun ProfileScreen(
    onProfileSetingClicked: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WappTheme.colors.backgroundBlack),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 25.dp),
        ) {
            Text(
                text = "프로필",
                style = WappTheme.typography.titleBold.copy(fontSize = 20.sp),
                color = WappTheme.colors.white,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 25.dp),
            )
            Image(
                painter =
                painterResource(id = com.wap.wapp.core.designresource.R.drawable.ic_subtract),
                contentDescription = stringResource(id = R.string.profile_setting_description),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 20.dp)
                    .clickable { onProfileSetingClicked() },
            )
        }
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}
