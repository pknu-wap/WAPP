package com.wap.wapp.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designresource.R.drawable
import com.wap.wapp.core.designresource.R.drawable.ic_normal_github

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
                painterResource(id = drawable.ic_subtract),
                contentDescription = stringResource(id = R.string.profile_setting_description),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 20.dp)
                    .clickable { onProfileSetingClicked() },
            )
        }

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(top = 30.dp, start = 10.dp, end = 10.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            WappTheme.colors.yellow3C,
                            WappTheme.colors.yellow34,
                            WappTheme.colors.yellowA4,
                        ),
                    ),
                ),
            ) {
                Image(
                    painter = painterResource(id = ic_normal_github),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 20.dp),
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.padding(start = 5.dp),
                ) {
                    Text(
                        text = "태규 님",
                        style = WappTheme.typography.contentRegular.copy(fontSize = 20.sp),
                        color = WappTheme.colors.white,
                    )

                    Text(
                        text = "일반 회원",
                        style = WappTheme.typography.labelRegular,
                        color = WappTheme.colors.white,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}
