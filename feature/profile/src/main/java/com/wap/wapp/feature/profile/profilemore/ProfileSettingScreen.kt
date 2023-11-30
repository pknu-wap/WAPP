package com.wap.wapp.feature.profile.profilemore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
internal fun ProfileSettingScreen(
    onClickedAlarmSetting: () -> Unit = {},
    onClickedLogout: () -> Unit = {},
    onClickedWithdrawal: () -> Unit = {},
    onClickedInquriy: () -> Unit = {},
    onClickedFAQ: () -> Unit = {},
    onClickedTermsAndPolicies: () -> Unit = {},
    onClickedPrivacyPolicy: () -> Unit = {},
) {

    val dividerThickness = 1.dp
    val dividerColor = WappTheme.colors.black42

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WappTheme.colors.backgroundBlack),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.padding(start = 15.dp, top = 20.dp, bottom = 25.dp),
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
            onClicked = onClickedAlarmSetting,
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = "로그아웃",
            onClicked = onClickedLogout,
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = "회원탈퇴",
            onClicked = onClickedWithdrawal,
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
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
        }

        WappRowBar(
            title = "문의하기",
            onClicked = onClickedInquriy,
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = "FAQ",
            onClicked = onClickedFAQ,
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = "약관 및 정책",
            onClicked = onClickedTermsAndPolicies,
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = "개인정보 처리 방침",
            onClicked = onClickedPrivacyPolicy,
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )
    }
}

@Preview
@Composable
fun PreviewProfileMoreScreen() {
    ProfileSettingScreen()
}
