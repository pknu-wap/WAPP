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
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappRowBar
import com.wap.designsystem.component.WappTopBar
import com.wap.wapp.core.designresource.R
import com.wap.wapp.feature.profile.R.string

@Composable
internal fun ProfileSettingRoute(
    navigateToProfile: () -> Unit,
    viewModel: ProfileSettingViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    ProfileSettingScreen(
        navigateToProfile = navigateToProfile,
        onClickedPrivacyPolicy = {
            navigateToUri(
                context,
                "https://www.notion.so/46beb7c4f3c2417bbec20eafd610d580?pvs=11",
            )
        },
        onClickedFAQ = {
            navigateToUri(
                context,
                "https://www.notion.so/FAQ-5832683da65048549e6a976b54d62875",
            )
        },
        onClickedInquiry = {
            navigateToUri(
                context,
                "https://www.notion.so/4fcb60f346c041248fd0d97d202a8a9a",
            )
        },
        onClickedTermsAndPolicies = {
            navigateToUri(
                context,
                "https://www.notion.so/042dc914a6a34093a51658693e009411",
            )
        },
    )
}

private fun navigateToUri(context: Context, url: String) = startActivity(
    context,
    generateUriIntent(url),
    null,
)

private fun generateUriIntent(url: String) = Intent(Intent.ACTION_VIEW, url.toUri())

@Composable
internal fun ProfileSettingScreen(
    navigateToProfile: () -> Unit,
    onClickedAlarmSetting: () -> Unit = {},
    onClickedSignOut: () -> Unit = {},
    onClickedWithdrawal: () -> Unit = {},
    onClickedInquiry: () -> Unit,
    onClickedFAQ: () -> Unit,
    onClickedTermsAndPolicies: () -> Unit,
    onClickedPrivacyPolicy: () -> Unit,
) {
    val dividerThickness = 1.dp
    val dividerColor = WappTheme.colors.black42

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = WappTheme.colors.backgroundBlack),
    ) {
        WappTopBar(
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
            onClicked = onClickedSignOut,
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = stringResource(id = com.wap.wapp.feature.profile.R.string.withdrawal),
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
                text = stringResource(id = com.wap.wapp.feature.profile.R.string.more),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )
        }

        WappRowBar(
            title = stringResource(id = com.wap.wapp.feature.profile.R.string.inquiry),
            onClicked = onClickedInquiry,
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = stringResource(id = com.wap.wapp.feature.profile.R.string.faq),
            onClicked = onClickedFAQ,
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = stringResource(id = com.wap.wapp.feature.profile.R.string.terms_and_policies),
            onClicked = onClickedTermsAndPolicies,
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )

        WappRowBar(
            title = stringResource(id = com.wap.wapp.feature.profile.R.string.privacy_policy),
            onClicked = onClickedPrivacyPolicy,
        )

        Divider(
            color = dividerColor,
            thickness = dividerThickness,
        )
    }
}
