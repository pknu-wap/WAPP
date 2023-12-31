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
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.feature.management.R
import com.wap.wapp.feature.management.registration.component.DeadlineCard
import com.wap.wapp.feature.management.registration.component.RegistrationTextField
import com.wap.wapp.feature.management.registration.component.RegistrationTitle
import com.wap.wapp.feature.management.registration.component.WappDatePickerDialog
import com.wap.wapp.feature.management.registration.component.WappTimePickerDialog
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventRegistrationContent(
    eventRegistrationState: EventRegistrationState,
    modifier: Modifier = Modifier,
    eventTitle: String,
    eventContent: String,
    location: String,
    startDate: LocalDate,
    startTime: LocalTime,
    endDate: LocalDate,
    endTime: LocalTime,
    showStartDatePicker: Boolean,
    showStartTimePicker: Boolean,
    showEndDatePicker: Boolean,
    showEndTimePicker: Boolean,
    timePickerState: TimePickerState,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onEndDateChanged: (LocalDate) -> Unit,
    onEndTimeChanged: (LocalTime) -> Unit,
    onStartDateChanged: (LocalDate) -> Unit,
    onStartTimeChanged: (LocalTime) -> Unit,
    onStartDatePickerStateChanged: (Boolean) -> Unit,
    onStartTimePickerStateChanged: (Boolean) -> Unit,
    onEndDatePickerStateChanged: (Boolean) -> Unit,
    onEndTimePickerStateChanged: (Boolean) -> Unit,
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
                startDate = startDate,
                startTime = startTime,
                endDate = endDate,
                endTime = endTime,
                timePickerState = timePickerState,
                onLocationChanged = onLocationChanged,
                onEndDateChanged = onEndDateChanged,
                onEndTimeChanged = onEndTimeChanged,
                onStartDateChanged = onStartDateChanged,
                onStartTimeChanged = onStartTimeChanged,
                showStartDatePicker = showStartDatePicker,
                showStartTimePicker = showStartTimePicker,
                showEndDatePicker = showEndDatePicker,
                showEndTimePicker = showEndTimePicker,
                onStartDatePickerStateChanged = onStartDatePickerStateChanged,
                onStartTimePickerStateChanged = onStartTimePickerStateChanged,
                onEndDatePickerStateChanged = onEndDatePickerStateChanged,
                onEndTimePickerStateChanged = onEndTimePickerStateChanged,
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
    startDate: LocalDate,
    startTime: LocalTime,
    endDate: LocalDate,
    endTime: LocalTime,
    timePickerState: TimePickerState,
    showStartDatePicker: Boolean,
    showStartTimePicker: Boolean,
    showEndDatePicker: Boolean,
    showEndTimePicker: Boolean,
    onStartDatePickerStateChanged: (Boolean) -> Unit,
    onStartTimePickerStateChanged: (Boolean) -> Unit,
    onEndDatePickerStateChanged: (Boolean) -> Unit,
    onEndTimePickerStateChanged: (Boolean) -> Unit,
    onLocationChanged: (String) -> Unit,
    onStartDateChanged: (LocalDate) -> Unit,
    onStartTimeChanged: (LocalTime) -> Unit,
    onEndDateChanged: (LocalDate) -> Unit,
    onEndTimeChanged: (LocalTime) -> Unit,
    onRegisterButtonClicked: () -> Unit,
) {
    if (showEndDatePicker) {
        WappDatePickerDialog(
            date = endDate,
            onDismissRequest = { onEndDatePickerStateChanged(false) },
            onDateChanged = onEndDateChanged,
        )
    }

    if (showEndTimePicker) {
        WappTimePickerDialog(
            state = timePickerState,
            onDismissRequest = { onEndTimePickerStateChanged(false) },
            onConfirmButtonClicked = { localTime ->
                onEndTimeChanged(localTime)
                onEndTimePickerStateChanged(false)
            },
            onDismissButtonClicked = {
                onEndTimePickerStateChanged(false)
            },
        )
    }

    if (showStartDatePicker) {
        WappDatePickerDialog(
            date = startDate,
            onDismissRequest = { onStartDatePickerStateChanged(false) },
            onDateChanged = onStartDateChanged,
        )
    }

    if (showStartTimePicker) {
        WappTimePickerDialog(
            state = timePickerState,
            onDismissRequest = { onStartTimePickerStateChanged(false) },
            onConfirmButtonClicked = { localTime ->
                onStartTimeChanged(localTime)
                onStartTimePickerStateChanged(false)
            },
            onDismissButtonClicked = {
                onStartTimePickerStateChanged(false)
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
                title = stringResource(R.string.start_date),
                hint = startDate.format(DateUtil.yyyyMMddFormatter),
                onCardClicked = {
                    onStartDatePickerStateChanged(true)
                },
                modifier = Modifier.padding(top = 20.dp),
            )

            DeadlineCard(
                title = stringResource(R.string.start_time),
                hint = startTime.format(DateUtil.HHmmFormatter),
                onCardClicked = {
                    onStartTimePickerStateChanged(true)
                },
                modifier = Modifier.padding(top = 20.dp),
            )

            DeadlineCard(
                title = stringResource(R.string.end_date),
                hint = endDate.format(DateUtil.yyyyMMddFormatter),
                onCardClicked = {
                    onEndDatePickerStateChanged(true)
                },
                modifier = Modifier.padding(top = 20.dp),
            )

            DeadlineCard(
                title = stringResource(R.string.end_time),
                hint = endTime.format(DateUtil.HHmmFormatter),
                onCardClicked = {
                    onEndTimePickerStateChanged(true)
                },
                modifier = Modifier.padding(top = 20.dp),
            )
        }

        WappButton(
            onClick = onRegisterButtonClicked,
            textRes = R.string.edit_complete,
            modifier = Modifier.padding(bottom = 20.dp),
        )
    }
}
