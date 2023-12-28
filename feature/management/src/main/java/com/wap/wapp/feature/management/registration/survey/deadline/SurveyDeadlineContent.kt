package com.wap.wapp.feature.management.registration.survey.deadline

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerState
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
import com.wap.designsystem.component.WappTitle
import com.wap.wapp.feature.management.R
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
    datePickerState: DatePickerState,
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
            onDismissRequest = { onDatePickerStateChanged(false) },
            onConfirmButtonClicked = {},
            onDateSelected = {},
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
        WappTitle(
            title = stringResource(R.string.survey_deadline_title),
            content = stringResource(R.string.survey_deadline_content),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            SurveyDeadlineCard(
                title = stringResource(R.string.date),
                hint = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                onCardClicked = {
                    onDatePickerStateChanged(true)
                },
            )

            SurveyDeadlineCard(
                title = stringResource(R.string.time),
                hint = time.format(DateTimeFormatter.ofPattern("HH.mm")),
                onCardClicked = {
                    onTimePickerStateChanged(true)
                },
            )
        }

        WappButton(
            textRes = R.string.register_survey,
            onClick = onRegisterButtonClicked,
        )
    }
}

@Composable
private fun SurveyDeadlineCard(
    title: String,
    hint: String,
    onCardClicked: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = WappTheme.typography.titleBold,
            color = WappTheme.colors.white,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(2f),
        )

        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .weight(3f)
                .clickable { onCardClicked() },
            colors = CardDefaults.cardColors(
                containerColor = WappTheme.colors.black25,
            ),
        ) {
            Text(
                text = hint,
                style = WappTheme.typography.contentMedium,
                color = WappTheme.colors.white,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
            )
        }
    }
}

private fun Long.toLocalDate(): LocalDate {
    val instant = Instant.ofEpochMilli(this)
    val instantAtSeoul = instant.atZone(ZoneId.of("Asia/Seoul"))
    return instantAtSeoul.toLocalDate()
}
