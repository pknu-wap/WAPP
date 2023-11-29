package com.wap.wapp.feature.management.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.feature.management.R
import com.wap.wapp.feature.management.component.RegistrationTextField
import com.wap.wapp.feature.management.component.RegistrationTitle

@Composable
internal fun EventRegistrationContent(
    eventRegistrationState: EventRegistrationState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        when (eventRegistrationState) {
            EventRegistrationState.EVENT_DETAILS -> EventDetailsContent(
                onTitleChange = {},
            )

            EventRegistrationState.EVENT_SCHEDULE -> EventScheduleContent()
        }
    }
}

@Composable
private fun EventDetailsContent(
    onTitleChange: (String) -> Unit,
) {
    RegistrationTitle(
        title = stringResource(id = R.string.event_details_title),
        content = stringResource(id = R.string.event_details_content),
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(top = 50.dp),
    ) {
        Text(
            text = stringResource(R.string.event_title),
            style = WappTheme.typography.titleBold,
            color = WappTheme.colors.white,
        )

        RegistrationTextField(
            value = stringResource(id = R.string.event_title),
            onValueChange = onTitleChange,
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
            value = stringResource(id = R.string.event_description),
            onValueChange = onTitleChange,
            placeholder = stringResource(R.string.event_description_hint),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun EventScheduleContent() {
    RegistrationTitle(
        title = stringResource(id = R.string.event_schedule_title),
        content = stringResource(id = R.string.event_schedule_content),
    )
}
