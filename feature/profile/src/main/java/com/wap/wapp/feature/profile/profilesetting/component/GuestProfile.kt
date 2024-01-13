package com.wap.wapp.feature.profile.profilesetting.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme
import com.wap.wapp.feature.profile.R

@Composable
internal fun GuestProfile(navigateToSignIn: () -> Unit) {
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
            .clickable { navigateToSignIn() },
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
