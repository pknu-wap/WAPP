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
        Dialog(
            onDismissRequest = { showLogoutDialog = false },
        ) {
        }
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
                text = stringResource(id = com.wap.wapp.feature.profile.R.string.account_setting),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )
        }

        WappRowBar(
            title = stringResource(id = com.wap.wapp.feature.profile.R.string.alarm_setting),
            onClicked = onClickedAlarmSetting,
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = stringResource(id = com.wap.wapp.feature.profile.R.string.sign_out),
            onClicked = { showLogoutDialog = true },
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = stringResource(id = com.wap.wapp.feature.profile.R.string.withdrawal),
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
                text = stringResource(id = com.wap.wapp.feature.profile.R.string.more),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )
        }

        WappRowBar(
            title = stringResource(id = com.wap.wapp.feature.profile.R.string.inquiry),
            onClicked = { navigateToUri(context, INQUIRY_URL) },
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = stringResource(id = com.wap.wapp.feature.profile.R.string.faq),
            onClicked = { navigateToUri(context, FAQ_URL) },
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = stringResource(id = com.wap.wapp.feature.profile.R.string.terms_and_policies),
            onClicked = { navigateToUri(context, TERMS_AND_POLICIES_URL) },
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = stringResource(id = com.wap.wapp.feature.profile.R.string.privacy_policy),
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
