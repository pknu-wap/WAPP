package com.wap.wapp.feature.management.survey.registration

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.feature.management.survey.R
import com.wap.wapp.feature.management.survey.component.WappDatePickerDialog
import com.wap.wapp.feature.management.survey.component.WappTimePickerDialog
import java.time.LocalDate
import java.time.LocalTime

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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        WappTitle(
            title = stringResource(R.string.survey_deadline_title),
            content = stringResource(R.string.survey_deadline_content),
            modifier = Modifier.padding(top = 10.dp, bottom = 24.dp),
        )

        DeadlineCard(
            title = stringResource(R.string.date),
            hint = date.format(DateUtil.yyyyMMddFormatter),
            onCardClicked = {
                onDatePickerStateChanged(true)
            },
        )

        DeadlineCard(
            title = stringResource(R.string.time),
            hint = time.format(DateUtil.HHmmFormatter),
            onCardClicked = {
                onTimePickerStateChanged(true)
            },
            modifier = Modifier.padding(top = 20.dp),
        )

        Spacer(modifier = Modifier.weight(1f))

        WappButton(
            textRes = R.string.register_survey,
            onClick = onRegisterButtonClicked,
            modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
        )
    }
}

@Composable
private fun DeadlineCard(
    title: String,
    hint: String,
    onCardClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
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
