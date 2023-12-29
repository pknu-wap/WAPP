package com.wap.wapp.feature.management.registration.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton
import com.wap.wapp.feature.management.R
import com.wap.wapp.feature.management.registration.component.DeadlineCard
import com.wap.wapp.feature.management.registration.component.RegistrationTextField
import com.wap.wapp.feature.management.registration.component.RegistrationTitle
import com.wap.wapp.feature.management.registration.component.WappDatePickerDialog
import com.wap.wapp.feature.management.registration.component.WappTimePickerDialog
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventRegistrationContent(
    eventRegistrationState: EventRegistrationState,
    modifier: Modifier = Modifier,
    eventTitle: String,
    eventContent: String,
    location: String,
    date: LocalDate,
    time: LocalTime,
    showDatePicker: Boolean,
    showTimePicker: Boolean,
    timePickerState: TimePickerState,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onDateChanged: (LocalDate) -> Unit,
    onTimeChanged: (LocalTime) -> Unit,
    onDatePickerStateChanged: (Boolean) -> Unit,
    onTimePickerStateChanged: (Boolean) -> Unit,
    onNextButtonClicked: () -> Unit,
    onRegisterButtonClicked: () -> Unit,
) {
    Column(modifier = modifier) {
        when (eventRegistrationState) {
            EventRegistrationState.EVENT_DETAILS -> EventDetailsContent(
                eventTitle = eventTitle,
                eventContent = eventContent,
                onTitleChanged = onTitleChanged,
                onContentChanged = onContentChanged,
                onNextButtonClicked = onNextButtonClicked,
            )

            EventRegistrationState.EVENT_SCHEDULE -> EventScheduleContent(
                location = location,
                date = date,
                time = time,
                timePickerState = timePickerState,
                onLocationChanged = onLocationChanged,
                onDateChanged = onDateChanged,
                onTimeChanged = onTimeChanged,
                showDatePicker = showDatePicker,
                showTimePicker = showTimePicker,
                onDatePickerStateChanged = onDatePickerStateChanged,
                onTimePickerStateChanged = onTimePickerStateChanged,
                onRegisterButtonClicked = onRegisterButtonClicked,
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
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
                .weight(1f),
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
                placeholder = stringResource(R.string.event_content_hint),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
            )
        }

        WappButton(
            onClick = onNextButtonClicked,
            textRes = R.string.next,
            modifier = Modifier.padding(bottom = 20.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventScheduleContent(
    location: String,
    date: LocalDate,
    time: LocalTime,
    timePickerState: TimePickerState,
    showDatePicker: Boolean,
    showTimePicker: Boolean,
    onDatePickerStateChanged: (Boolean) -> Unit,
    onTimePickerStateChanged: (Boolean) -> Unit,
    onLocationChanged: (String) -> Unit,
    onDateChanged: (LocalDate) -> Unit,
    onTimeChanged: (LocalTime) -> Unit,
    onRegisterButtonClicked: () -> Unit,
) {
    if (showDatePicker) {
        WappDatePickerDialog(
            date = date,
            onDismissRequest = { onDatePickerStateChanged(false) },
            onDateChanged = onDateChanged,
        )
    }

    if (showTimePicker) {
        WappTimePickerDialog(
            state = timePickerState,
            onDismissRequest = { onTimePickerStateChanged(false) },
            onConfirmButtonClicked = { localTime ->
                onTimeChanged(localTime)
                onTimePickerStateChanged(false)
            },
            onDismissButtonClicked = {
                onTimePickerStateChanged(false)
            },
        )
    }

    RegistrationTitle(
        title = stringResource(id = R.string.event_schedule_title),
        content = stringResource(id = R.string.event_schedule_content),
    )
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(top = 50.dp)
                .weight(1f),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.event_location),
                    style = WappTheme.typography.titleBold,
                    color = WappTheme.colors.white,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(2f),
                )

                RegistrationTextField(
                    value = location,
                    onValueChange = onLocationChanged,
                    placeholder = stringResource(R.string.event_location_hint),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(3f),
                )
            }

            DeadlineCard(
                title = stringResource(R.string.date),
                hint = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                onCardClicked = {
                    onDatePickerStateChanged(true)
                },
                modifier = Modifier.padding(top = 20.dp),
            )

            DeadlineCard(
                title = stringResource(R.string.time),
                hint = time.format(DateTimeFormatter.ofPattern("HH.mm")),
                onCardClicked = {
                    onTimePickerStateChanged(true)
                },
                modifier = Modifier.padding(top = 20.dp),
            )
        }

        WappButton(
            onClick = onRegisterButtonClicked,
            textRes = R.string.register_event,
            modifier = Modifier.padding(bottom = 20.dp),
        )
    }
}
