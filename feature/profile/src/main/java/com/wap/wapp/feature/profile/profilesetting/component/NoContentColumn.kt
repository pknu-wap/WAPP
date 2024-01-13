package com.wap.wapp.feature.profile.profilesetting.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme

@Composable
internal fun NothingToShow(@StringRes title: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
            .height(130.dp),
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(id = title),
            style = WappTheme.typography.contentRegular.copy(fontSize = 20.sp),
            color = WappTheme.colors.white,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
