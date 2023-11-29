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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import com.wap.wapp.core.designresource.R.drawable
import com.wap.wapp.feature.profile.component.WappProfileCard

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

        WappProfileCard(isManager = true, userName = "태규")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
        ) {
            Text(
                text = "출석",
                style = WappTheme.typography.titleBold.copy(fontSize = 20.sp),
                color = WappTheme.colors.white,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 25.dp, bottom = 10.dp),
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 15.dp),
            ) {
                Image(
                    painter = painterResource(id = drawable.ic_green_circle),
                    contentDescription = "",
                )

                Text(
                    text = "출석",
                    style = WappTheme.typography.labelRegular,
                    color = WappTheme.colors.white,
                )

                Image(
                    painter = painterResource(id = drawable.ic_red_circle),
                    contentDescription = "",
                )

                Text(
                    text = "결석",
                    style = WappTheme.typography.labelRegular,
                    color = WappTheme.colors.white,
                )
            }
        }

        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 160.dp),
        ) {
            LazyColumn() {
            }
        }

        Text(
            text = "내가 한 설문",
            style = WappTheme.typography.titleBold.copy(fontSize = 20.sp),
            color = WappTheme.colors.white,
            modifier = Modifier
                .padding(start = 25.dp, top = 45.dp, bottom = 10.dp),
        )

        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 160.dp),
        ) {
            LazyColumn() {
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen()
}
