package com.wap.wapp.feature.profile.profilemore

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designresource.R

@Composable
internal fun ProfileMoreScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.padding(start = 15.dp),
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
    }
}

@Preview
@Composable
fun PreviewProfileMoreScreen() {
    ProfileMoreScreen()
}
