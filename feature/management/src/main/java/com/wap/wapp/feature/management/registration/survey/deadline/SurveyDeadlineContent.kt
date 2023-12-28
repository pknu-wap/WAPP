package com.wap.wapp.feature.management.registration.survey.deadline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.component.WappButton
import com.wap.designsystem.component.WappTitle
import com.wap.wapp.feature.management.R
import com.wap.wapp.feature.management.registration.component.DeadlineCard
import com.wap.wapp.feature.management.registration.component.WappDatePickerDialog
import com.wap.wapp.feature.management.registration.component.WappTimePickerDialog
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SurveyDeadlineContent(
    date: LocalDate,
    time: LocalTime,
    timePickerState: TimePickerState,
    showDatePicker: Boolean,
    showTimePicker: Boolean,
    onDatePickerStateChanged: (Boolean) -> Unit,
    onTimePickerStateChanged: (Boolean) -> Unit,
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

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.weight(1f),
        ) {
            WappTitle(
                title = stringResource(R.string.survey_deadline_title),
                content = stringResource(R.string.survey_deadline_content),
            )

            DeadlineCard(
                title = stringResource(R.string.date),
                hint = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                onCardClicked = {
                    onDatePickerStateChanged(true)
                },
                modifier = Modifier.padding(top = 40.dp),
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
            textRes = R.string.register_survey,
            onClick = onRegisterButtonClicked,
            modifier = Modifier.padding(bottom = 20.dp),
        )
    }
}

private fun Long.toLocalDate(): LocalDate {
    val instant = Instant.ofEpochMilli(this)
    val instantAtSeoul = instant.atZone(ZoneId.of("Asia/Seoul"))
    return instantAtSeoul.toLocalDate()
}
