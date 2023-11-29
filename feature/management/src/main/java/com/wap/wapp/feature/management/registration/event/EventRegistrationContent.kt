package com.wap.wapp.feature.management.registration.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton
import com.wap.wapp.feature.management.R
import com.wap.wapp.feature.management.registration.component.RegistrationTextField
import com.wap.wapp.feature.management.registration.component.RegistrationTitle

@Composable
internal fun EventRegistrationContent(
    eventRegistrationState: EventRegistrationState,
    modifier: Modifier = Modifier,
    eventTitle: String,
    eventContent: String,
    eventLocation: String,
    eventDate: String,
    eventTime: String,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onDateChanged: (String) -> Unit,
    onTimeChanged: (String) -> Unit,
    onNextButtonClicked: (EventRegistrationState) -> Unit,
    onRegisterButtonClicked: () -> Unit,
) {
    Column(modifier = modifier) {
        when (eventRegistrationState) {
            EventRegistrationState.EVENT_DETAILS -> EventDetailsContent(
                eventTitle = eventTitle,
                eventContent = eventContent,
                onTitleChanged = onTitleChanged,
                onContentChanged = onContentChanged,
                onNextButtonClicked =
                { onNextButtonClicked(EventRegistrationState.EVENT_SCHEDULE) },
            )

            EventRegistrationState.EVENT_SCHEDULE -> EventScheduleContent(
                eventLocation = eventLocation,
                eventDate = eventDate,
                eventTime = eventTime,
                onLocationChanged = onLocationChanged,
                onDateChanged = onDateChanged,
                onTimeChanged = onTimeChanged,
                onNextButtonClicked = onRegisterButtonClicked,
            )
        }
    }
}

@Composable
private fun EventDetailsContent(
    eventTitle: String,
    eventContent: String,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onNextButtonClicked: () -> Unit,
) {
    RegistrationTitle(
        title = stringResource(id = R.string.event_details_title),
        content = stringResource(id = R.string.event_details_content),
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(top = 50.dp)
                .align(Alignment.TopCenter),
        ) {
            Text(
                text = stringResource(R.string.event_title),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )

            RegistrationTextField(
                value = eventTitle,
                onValueChange = onTitleChanged,
                placeholder = stringResource(R.string.event_title_hint),
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = stringResource(R.string.event_schedule_title),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
                modifier = Modifier.padding(top = 30.dp),
            )

            RegistrationTextField(
                value = eventContent,
                onValueChange = onContentChanged,
                placeholder = stringResource(R.string.event_description_hint),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            )
        }

        WappButton(
            onClick = onNextButtonClicked,
            textRes = R.string.next,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
        )
    }
}

@Composable
private fun EventScheduleContent(
    eventLocation: String,
    eventDate: String,
    eventTime: String,
    onLocationChanged: (String) -> Unit,
    onDateChanged: (String) -> Unit,
    onTimeChanged: (String) -> Unit,
    onNextButtonClicked: () -> Unit,
) {
    RegistrationTitle(
        title = stringResource(id = R.string.event_schedule_title),
        content = stringResource(id = R.string.event_schedule_content),
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(top = 50.dp)
                .align(Alignment.TopCenter),
        ) {
            Text(
                text = stringResource(R.string.event_title),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
            )

            RegistrationTextField(
                value = eventLocation,
                onValueChange = onLocationChanged,
                placeholder = stringResource(R.string.event_title_hint),
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = stringResource(R.string.event_schedule_title),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
                modifier = Modifier.padding(top = 30.dp),
            )

            RegistrationTextField(
                value = eventDate,
                onValueChange = onDateChanged,
                placeholder = stringResource(R.string.event_description_hint),
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = stringResource(R.string.event_schedule_title),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
                modifier = Modifier.padding(top = 30.dp),
            )

            RegistrationTextField(
                value = eventTime,
                onValueChange = onTimeChanged,
                placeholder = stringResource(R.string.event_description_hint),
                modifier = Modifier.fillMaxWidth(),
            )
        }

        WappButton(
            onClick = onNextButtonClicked,
            textRes = R.string.register_event,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
        )
    }
}
