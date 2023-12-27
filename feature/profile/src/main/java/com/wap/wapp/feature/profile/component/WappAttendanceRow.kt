package com.wap.wapp.feature.profile.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designresource.R

@Composable
internal fun WappAttendacneRow(
    isAttendance: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(horizontal = 10.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f),
        ) {
            WappAttendanceBadge(isAttendance = isAttendance)
            Text(
                text = "프로젝트 세미나",
                style = WappTheme.typography.labelRegular,
                color = WappTheme.colors.white,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 10.dp),
            )
        }
        Text(
            text = "09월 04일",
            style = WappTheme.typography.labelRegular,
            color = WappTheme.colors.gray95,
            modifier = Modifier.padding(start = 10.dp),
        )
    }
}

@Composable
private fun WappAttendanceBadge(isAttendance: Boolean = true) {
    val drawableId = if (isAttendance) R.drawable.ic_attendance else R.drawable.ic_absent
    Image(painter = painterResource(id = drawableId), contentDescription = "")
}
