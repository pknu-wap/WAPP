package com.wap.wapp.feature.profile.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme

@Composable
internal fun WappAttendacneRow() {
    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f),
        ) {
            WappAttendanceBadge()
            Text(
                text = "프로젝트 세미나",
                style = WappTheme.typography.labelRegular,
                color = WappTheme.colors.white,
                modifier = Modifier.padding(start = 10.dp),
            )
        }
        Text(
            text = "09월 04일",
            style = WappTheme.typography.labelRegular,
            color = WappTheme.colors.white,
        )
    }
}

@Composable
private fun WappAttendanceBadge() {}
