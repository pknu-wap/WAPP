package com.wap.wapp.feature.management.event

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.wap.wapp.feature.management.R
import com.wap.wapp.feature.management.component.RegistrationTitle

@Composable
internal fun EventRegistrationContent(
    eventRegistrationState: EventRegistrationState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        when (eventRegistrationState) {
            EventRegistrationState.EVENT_DETAILS -> EventDetailsContent()
            EventRegistrationState.EVENT_SCHEDULE -> EventScheduleContent()
        }
    }
}

@Composable
private fun EventDetailsContent() {
    RegistrationTitle(
        title = stringResource(id = R.string.event_details_title),
        content = stringResource(id = R.string.event_details_content),
    )
}

@Composable
private fun EventScheduleContent() {
    RegistrationTitle(
        title = stringResource(id = R.string.event_schedule_title),
        content = stringResource(id = R.string.event_schedule_content),
    )
}
