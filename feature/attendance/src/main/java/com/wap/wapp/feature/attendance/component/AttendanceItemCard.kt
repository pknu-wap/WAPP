package com.wap.wapp.feature.attendance.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.feature.attendance.R

@Composable
internal fun AttendanceItemCard(
    eventTitle: String,
    eventContent: String,
    remainAttendanceDateTime: String,
    isAttendance: Boolean,
    onSelectItemCard: () -> Unit = {},
) {
    val cardModifier = if (isAttendance) {
        Modifier.fillMaxWidth()
    } else {
        Modifier
            .fillMaxWidth()
            .clickable { onSelectItemCard() }
    }
    Card(
        colors = CardDefaults.cardColors(containerColor = WappTheme.colors.black25),
        modifier = cardModifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = eventTitle,
                    color = WappTheme.colors.white,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = WappTheme.typography.titleBold,
                )
                if (isAttendance) {
                    Text(
                        text = stringResource(id = R.string.attendance_complete),
                        color = WappTheme.colors.greenAB,
                        style = WappTheme.typography.captionMedium,
                    )
                } else {
                    Text(
                        text = remainAttendanceDateTime,
                        color = WappTheme.colors.yellow34,
                        style = WappTheme.typography.captionMedium,
                    )
                }
            }

            Text(
                text = eventContent,
                color = WappTheme.colors.grayBD,
                style = WappTheme.typography.contentMedium,
            )
        }
    }
}
