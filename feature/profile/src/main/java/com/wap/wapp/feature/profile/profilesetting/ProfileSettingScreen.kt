package com.wap.wapp.feature.profile.profilesetting

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappRowBar
import com.wap.designsystem.component.WappSubTopBar
import com.wap.wapp.core.designresource.R
import com.wap.wapp.feature.profile.R.string
import com.wap.wapp.feature.profile.profilesetting.component.ProfileSettingDialog

@Composable
internal fun ProfileSettingRoute(
    navigateToProfile: () -> Unit,
    viewModel: ProfileSettingViewModel = hiltViewModel(),
) {
    ProfileSettingScreen(
        navigateToProfile = navigateToProfile,
    )
}

@Composable
internal fun ProfileSettingScreen(
    navigateToProfile: () -> Unit,
    onClickedAlarmSetting: () -> Unit = {},
) {
    var showWithdrawalDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    val dividerThickness = 1.dp
    val dividerColor = WappTheme.colors.black42
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    if (showWithdrawalDialog) {
        Dialog(
            onDismissRequest = { showWithdrawalDialog = false },
        ) {
        }
    }

    if (showLogoutDialog) {
        ProfileSettingDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = string.logout,
            content = generateLogoutDialogString(),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(color = WappTheme.colors.backgroundBlack),
    ) {
        WappSubTopBar(
            titleRes = string.more,
            showLeftButton = true,
            onClickLeftButton = navigateToProfile,
            modifier = Modifier.padding(top = 20.dp),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.padding(start = 15.dp, top = 20.dp, bottom = 25.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_account_setting),
                contentDescription = "",
            )
            Text(
                text = stringResource(id = string.account_setting),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )
        }

        WappRowBar(
            title = stringResource(id = string.alarm_setting),
            onClicked = onClickedAlarmSetting,
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = stringResource(id = string.logout),
            onClicked = { showLogoutDialog = true },
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = stringResource(id = string.withdrawal),
            onClicked = { showWithdrawalDialog = true },
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
                text = stringResource(id = string.more),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )
        }

        WappRowBar(
            title = stringResource(id = string.inquiry),
            onClicked = { navigateToUri(context, INQUIRY_URL) },
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = stringResource(id = string.faq),
            onClicked = { navigateToUri(context, FAQ_URL) },
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = stringResource(id = string.terms_and_policies),
            onClicked = { navigateToUri(context, TERMS_AND_POLICIES_URL) },
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = stringResource(id = string.privacy_policy),
            onClicked = { navigateToUri(context, PRIVACY_POLICY_URL) },
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )
    }
}

private fun navigateToUri(context: Context, url: String) = startActivity(
    context,
    generateUriIntent(url),
    null,
)

private fun generateUriIntent(url: String) = Intent(Intent.ACTION_VIEW, url.toUri())

@Composable
private fun generateLogoutDialogString() = buildAnnotatedString {
    append("정말로 ")
    withStyle(
        style = SpanStyle(
            textDecoration = TextDecoration.Underline,
            color = WappTheme.colors.yellow34,
        ),
    ) {
        append("로그아웃")
    }
    append("을 원하신다면 ")
    withStyle(
        style = SpanStyle(
            textDecoration = TextDecoration.Underline,
            color = WappTheme.colors.yellow34,
        ),
    ) {
        append("완료")
    }
    append(" 버튼을 눌러주세요.")
}
