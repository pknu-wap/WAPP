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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappCard
import com.wap.wapp.core.designresource.R.drawable
import com.wap.wapp.core.designresource.R.string
import com.wap.wapp.feature.profile.component.WappAttendacneRow
import com.wap.wapp.feature.profile.component.WappProfileCard
import com.wap.wapp.feature.profile.component.WappSurveyHistoryRow

@Composable
internal fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToProfileSetting: () -> Unit,
    navigateToSignInScreen: () -> Unit,
) {
    ProfileScreen(
        navigateToProfileSetting = navigateToProfileSetting,
        navigateToSignInScreen = navigateToSignInScreen,
    )
}

@Composable
internal fun ProfileScreen(
    role: Role = Role.MANAGER,
    userName: String = "",
    navigateToProfileSetting: () -> Unit,
    navigateToSignInScreen: () -> Unit,
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
                text = stringResource(id = string.profile),
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
                    .clickable { navigateToProfileSetting() },
            )
        }

        WappProfileCard(role = role, userName = userName)

        if (role == Role.GUEST) {
            GuestScreen(navigateToSignInScreen = navigateToSignInScreen)
            return
        }

        UserScreen()
    }
}

@Composable
private fun GuestScreen(navigateToSignInScreen: () -> Unit) {
    Text(
        text = SpannableGuestText(),
        color = WappTheme.colors.white,
        style = WappTheme.typography.titleRegular.copy(fontSize = 26.sp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(top = 60.dp),
    )

    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = WappTheme.colors.yellow34,
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .padding(top = 40.dp)
            .height(50.dp)
            .fillMaxWidth()
            .clickable { navigateToSignInScreen() },
    ) {
        Text(
            text = stringResource(id = R.string.navigate_to_login),
            style = WappTheme.typography.contentMedium,
            color = WappTheme.colors.white,
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentHeight(),
        )
    }
}

@Composable
private fun SpannableGuestText() = buildAnnotatedString {
    append("로그인하여\n")
    withStyle(
        style = SpanStyle(
            color = WappTheme.colors.yellow34,
            textDecoration = TextDecoration.Underline,
        ),
    ) {
        append("WAPP")
    }
    append(" 와 ")
    withStyle(
        style = SpanStyle(
            color = WappTheme.colors.yellow34,
            textDecoration = TextDecoration.Underline,
        ),
    ) {
        append("추억")
    }
    append("을 쌓아보세요!")
}

@Composable
private fun UserScreen() {
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        WapAttendance(modifier = Modifier.padding(top = 20.dp))

        MyAttendanceStatus(modifier = Modifier.padding(top = 20.dp))

        MySurveyHistory(modifier = Modifier.padding(top = 20.dp))
    }
}

@Composable
private fun WapAttendance(modifier: Modifier) {
    WappCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "WAP 출석",
                    style = WappTheme.typography.captionBold.copy(fontSize = 20.sp),
                    color = WappTheme.colors.white,
                )

                Image(
                    painter = painterResource(id = drawable.ic_check),
                    contentDescription = "",
                    modifier = Modifier.padding(start = 10.dp),
                )
            }

            Text(
                text = "2023-12-23",
                style = WappTheme.typography.contentRegular,
                color = WappTheme.colors.white,
                modifier = Modifier.padding(top = 20.dp),
            )

            Text(
                text = "오늘은 별 다른 행사가 없어요!",
                style = WappTheme.typography.contentRegular.copy(fontSize = 20.sp),
                color = WappTheme.colors.white,
                modifier = Modifier.padding(top = 5.dp),
            )
        }
    }
}

@Composable
private fun MyAttendanceStatus(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.my_attendance),
            style = WappTheme.typography.titleBold.copy(fontSize = 20.sp),
            color = WappTheme.colors.white,
            modifier = Modifier.padding(start = 5.dp),
        )

        WappCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 10.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(vertical = 10.dp),
            ) {
                WappAttendacneRow(isAttendance = false)
                WappAttendacneRow(isAttendance = true)
                WappAttendacneRow(isAttendance = false)
                WappAttendacneRow(isAttendance = true)
            }
        }
    }
}

@Composable
private fun MySurveyHistory(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.survey_i_did),
            style = WappTheme.typography.titleBold.copy(fontSize = 20.sp),
            color = WappTheme.colors.white,
            modifier = Modifier.padding(start = 5.dp),
        )

        WappCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 10.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(vertical = 10.dp),
            ) {
                WappSurveyHistoryRow()
                WappSurveyHistoryRow()
                WappSurveyHistoryRow()
                WappSurveyHistoryRow()
            }
        }
    }
}
