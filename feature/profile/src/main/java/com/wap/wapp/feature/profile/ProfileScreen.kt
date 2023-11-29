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
    }
}

@Composable
fun WappProfileCard(
    isManager: Boolean,
    userName: String,
) {
    var position = "일반 회원"
    var githubImage = drawable.ic_normal_github
    var catImage = drawable.ic_normal_cat
    var brush = Brush.horizontalGradient(
        listOf(
            WappTheme.colors.yellow3C,
            WappTheme.colors.yellow34,
            WappTheme.colors.yellowA4,
        ),
    )

    if (isManager) {
        position = "운영진"
        githubImage = drawable.ic_manager_github
        catImage = drawable.ic_manager_cat
        brush = Brush.horizontalGradient(
            listOf(
                WappTheme.colors.yellow3C,
                WappTheme.colors.yellow34,
                WappTheme.colors.yellowA4,
            ),
        )
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(top = 30.dp, start = 10.dp, end = 10.dp),
    ) {
        Box(
            modifier = Modifier.background(
                brush = brush,
            ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterStart),
            ) {
                Image(
                    painter = painterResource(id = drawable.ic_normal_github),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 20.dp),
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.padding(start = 5.dp),
                ) {
                    Text(
                        text = "$userName 님",
                        style = WappTheme.typography.contentRegular.copy(fontSize = 20.sp),
                        color = WappTheme.colors.white,
                    )

                    Text(
                        text = position,
                        style = WappTheme.typography.labelRegular,
                        color = WappTheme.colors.white,
                    )
                }
            }

            Image(
                painter = painterResource(id = drawable.ic_normal_cat),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp, top = 25.dp),
            )
        }
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}
