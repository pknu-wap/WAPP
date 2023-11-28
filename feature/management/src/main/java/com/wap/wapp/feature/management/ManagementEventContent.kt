package com.wap.wapp.feature.management

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton
import com.wap.wapp.core.model.event.Event

@Composable
internal fun ManagementEventContent(
    eventList: List<Event>,
    onCardClicked: (Int) -> Unit,
    onAddEventButtonClicked: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = WappTheme.colors.black25,
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.survey),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                itemsIndexed(eventList) { currentIndex, event ->
                    ManagementEventItem(
                        item = event,
                        cardColor = ManagementCardColor(currentIndex = currentIndex),
                        onCardClicked = { eventId -> onCardClicked(eventId) },
                    )
                }
            }

            WappButton(
                textRes = R.string.add_survey,
                onClick = { onAddEventButtonClicked() },
            )
        }
    }
}

@Composable
private fun ManagementEventItem(
    item: Event,
    cardColor: Color,
    onCardClicked: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onCardClicked(item.eventId) },
        colors = CardDefaults.cardColors(containerColor = cardColor),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = item.content,
                    style = WappTheme.typography.contentMedium,
                    color = WappTheme.colors.white,
                    maxLines = 1,
                )

                Text(
                    text = item.period.toString(),
                    style = WappTheme.typography.captionMedium,
                    color = WappTheme.colors.white,
                    maxLines = 1,
                )
            }

            Icon(
                painter = painterResource(com.wap.wapp.core.designresource.R.drawable.ic_forward),
                contentDescription = stringResource(R.string.detail_icon_description),
                tint = WappTheme.colors.yellow,
                modifier = Modifier
                    .clickable { onCardClicked(item.eventId) }
                    .size(20.dp),
            )
        }
    }
}
