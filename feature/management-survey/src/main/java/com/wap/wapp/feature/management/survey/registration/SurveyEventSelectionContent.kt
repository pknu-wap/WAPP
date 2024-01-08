package com.wap.wapp.feature.management.survey.registration

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton
import com.wap.designsystem.component.WappTitle
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.feature.management.survey.R

@Composable
internal fun SurveyEventSelectionContent(
    eventList: List<Event>,
    selectedEvent: Event,
    onEventSelected: (Event) -> Unit,
    onNextButtonClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize(),
    ) {
        WappTitle(
            title = stringResource(R.string.event_selection_title),
            content = stringResource(R.string.event_selection_content),
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.heightIn(max = 400.dp),
        ) {
            items(eventList) { event ->
                EventCard(
                    event = event,
                    selectedEvent = selectedEvent,
                    onEventSelected = onEventSelected,
                )
            }
        }

        WappButton(
            textRes = R.string.next,
            onClick = onNextButtonClicked,
            modifier = Modifier.padding(bottom = 16.dp),
        )
    }
}

@Composable
private fun EventCard(
    event: Event,
    selectedEvent: Event,
    onEventSelected: (Event) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = WappTheme.colors.black25,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEventSelected(event) },
        border = BorderStroke(
            color = if (event.eventId == selectedEvent.eventId) {
                WappTheme.colors.yellow34
            } else {
                WappTheme.colors.black25
            },
            width = 1.dp,
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = event.title,
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )

            Text(
                text = event.content,
                style = WappTheme.typography.captionMedium,
                color = WappTheme.colors.yellow34,
            )
        }
    }
}
