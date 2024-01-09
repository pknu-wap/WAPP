package com.wap.wapp.feature.management

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import com.wap.designsystem.component.CircleLoader
import com.wap.designsystem.component.WappButton
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.model.event.Event

@Composable
internal fun ManagementEventCard(
    eventsState: ManagementViewModel.EventsState,
    onCardClicked: (String, String) -> Unit,
    onAddEventButtonClicked: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = WappTheme.colors.black25,
        ),
        modifier = Modifier.padding(horizontal = 8.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = stringResource(R.string.event),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )

            ManagementEventContent(
                eventsState = eventsState,
                onCardClicked = onCardClicked,
                onAddEventButtonClicked = onAddEventButtonClicked,
            )
        }
    }
}

@Composable
private fun ManagementEventContent(
    eventsState: ManagementViewModel.EventsState,
    onCardClicked: (String, String) -> Unit,
    onAddEventButtonClicked: () -> Unit,
) {
    when (eventsState) {
        is ManagementViewModel.EventsState.Loading -> CircleLoader(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
        )

        is ManagementViewModel.EventsState.Success -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.heightIn(max = 166.dp),
            ) {
                itemsIndexed(
                    items = eventsState.events,
                    key = { index, event -> event.eventId },
                ) { currentIndex, event ->
                    ManagementEventItem(
                        item = event,
                        cardColor = ManagementCardColor(currentIndex = currentIndex),
                        onCardClicked = onCardClicked,
                    )
                }
            }

            WappButton(
                textRes = R.string.event_registration,
                onClick = { onAddEventButtonClicked() },
            )
        }

        is ManagementViewModel.EventsState.Failure -> {}
    }
}

@Composable
private fun ManagementEventItem(
    item: Event,
    cardColor: Color,
    onCardClicked: (String, String) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .clickable { onCardClicked(item.startDateTime.toString(), item.eventId) },
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
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(1f),
            ) {
                Column {
                    Text(
                        text = item.title,
                        style = WappTheme.typography.titleBold,
                        color = WappTheme.colors.white,
                        maxLines = 1,
                    )

                    Text(
                        text = item.content,
                        style = WappTheme.typography.labelRegular,
                        color = WappTheme.colors.white,
                        maxLines = 1,
                    )
                }

                Text(
                    text = stringResource(
                        id = R.string.event_duration,
                        item.startDateTime.format(DateUtil.yyyyMMddFormatter),
                    ),
                    style = WappTheme.typography.captionRegular,
                    color = WappTheme.colors.white,
                )
            }

            Icon(
                painter = painterResource(com.wap.wapp.core.designresource.R.drawable.ic_forward),
                contentDescription = stringResource(R.string.detail_icon_description),
                tint = WappTheme.colors.yellow34,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}
