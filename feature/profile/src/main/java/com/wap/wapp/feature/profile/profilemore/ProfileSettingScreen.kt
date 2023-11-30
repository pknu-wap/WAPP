package com.wap.wapp.feature.profile.profilemore

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappRowBar
import com.wap.wapp.core.designresource.R

@Composable
internal fun ProfileSettingScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.padding(start = 15.dp, bottom = 25.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_account_setting),
                contentDescription = "",
            )
            Text(
                text = "계정 설정",
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )
        }

        WappRowBar(
            title = "알림 설정",
        )

        Divider(
            color = WappTheme.colors.gray82,
            thickness = 0.5.dp,
            modifier = Modifier.padding(vertical = 10.dp),
        )

        WappRowBar(
            title = "로그아웃",
        )

        Divider(
            color = WappTheme.colors.gray82,
            thickness = 0.5.dp,
            modifier = Modifier.padding(vertical = 10.dp),
        )

        WappRowBar(
            title = "회원탈퇴",
        )

        Divider(
            color = WappTheme.colors.gray82,
            thickness = 0.5.dp,
            modifier = Modifier.padding(vertical = 10.dp),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.padding(start = 15.dp, top = 25.dp, bottom = 25.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_more),
                contentDescription = "",
            )
            Text(
                text = "더보기",
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )

            WappRowBar(
                title = "문의하기",
            )

            Divider(
                color = WappTheme.colors.gray82,
                thickness = 0.5.dp,
                modifier = Modifier.padding(vertical = 10.dp),
            )

            WappRowBar(
                title = "FAQ",
            )

            Divider(
                color = WappTheme.colors.gray82,
                thickness = 0.5.dp,
                modifier = Modifier.padding(vertical = 10.dp),
            )

            WappRowBar(
                title = "약관 및 정책",
            )

            Divider(
                color = WappTheme.colors.gray82,
                thickness = 0.5.dp,
                modifier = Modifier.padding(vertical = 10.dp),
            )

            WappRowBar(
                title = "개인정보 처리 방침",
            )

            Divider(
                color = WappTheme.colors.gray82,
                thickness = 0.5.dp,
                modifier = Modifier.padding(vertical = 10.dp),
            )
        }
    }
}

@Preview
@Composable
fun PreviewProfileMoreScreen() {
    ProfileSettingScreen()
}
