package com.wap.wapp.feature.attendance.management.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.designresource.R.drawable.ic_forward_yellow
import com.wap.wapp.core.model.event.Event

@Composable
internal fun AttendanceItemCard(
    event: Event,
    onSelectItemCard: () -> Unit = {},
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = WappTheme.colors.black25),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelectItemCard() },
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = event.title,
                        color = WappTheme.colors.white,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        style = WappTheme.typography.titleBold,
                    )

                    Text(
                        text = event.getCalculatedTime(),
                        color = WappTheme.colors.yellow34,
                        style = WappTheme.typography.captionMedium,
                    )
                }

                Text(
                    text = event.content,
                    color = WappTheme.colors.grayBD,
                    style = WappTheme.typography.contentMedium,
                )
            }

            Image(
                painter = painterResource(id = ic_forward_yellow),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp),
            )
        }
    }
}
