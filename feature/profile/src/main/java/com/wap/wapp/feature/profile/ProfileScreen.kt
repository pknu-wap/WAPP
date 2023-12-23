package com.wap.wapp.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
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
import com.wap.wapp.core.designresource.R.drawable
import com.wap.wapp.core.designresource.R.string
import com.wap.wapp.feature.profile.component.WappProfileCard

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
    role: Role = Role.GUEST,
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
            GuestModeScreen(navigateToSignInScreen = navigateToSignInScreen)
            return
        }
        UserScreen()
    }
}

@Composable
private fun GuestModeScreen(navigateToSignInScreen: () -> Unit) {
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
private fun UserScreen() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
    ) {
        Text(
            text = stringResource(id = R.string.attendance),
            style = WappTheme.typography.titleBold.copy(fontSize = 20.sp),
            color = WappTheme.colors.white,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 25.dp, bottom = 10.dp),
        )
    }

    val cardModifier = Modifier
        .fillMaxWidth()
        .heightIn(max = 160.dp)
        .background(WappTheme.colors.black25)

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = cardModifier,
    ) {
        LazyColumn() {
        }
    }

    Text(
        text = stringResource(id = R.string.survey_i_did),
        style = WappTheme.typography.titleBold.copy(fontSize = 20.sp),
        color = WappTheme.colors.white,
        modifier = Modifier.padding(start = 25.dp, top = 45.dp, bottom = 10.dp),
    )

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = cardModifier,
    ) {
        LazyColumn() {
        }
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
