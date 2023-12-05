package com.wap.wapp.feature.management.registration.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappTopBar
import com.wap.wapp.feature.management.R

@Composable
internal fun EventRegistrationScreen(
    currentRegistrationState: EventRegistrationState,
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
    onBackButtonClicked: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = WappTheme.colors.backgroundBlack,
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // paddingValue padding
                .padding(16.dp), // dp value padding
        ) {
            WappTopBar(
                titleRes = R.string.event_registration,
                showLeftButton = true,
                onClickLeftButton = onBackButtonClicked,
            )

            EventRegistrationStateIndicator(
                eventRegistrationState = currentRegistrationState,
                modifier = Modifier.padding(top = 16.dp),
            )

            EventRegistrationContent(
                eventRegistrationState = currentRegistrationState,
                modifier = Modifier.padding(top = 50.dp),
                eventTitle = eventTitle,
                eventContent = eventContent,
                eventLocation = eventLocation,
                eventDate = eventDate,
                eventTime = eventTime,
                onTitleChanged = onTitleChanged,
                onContentChanged = onContentChanged,
                onLocationChanged = onLocationChanged,
                onDateChanged = onDateChanged,
                onTimeChanged = onTimeChanged,
                onNextButtonClicked = onNextButtonClicked,
                onRegisterButtonClicked = onRegisterButtonClicked,
            )
        }
    }
}

@Composable
private fun EventRegistrationStateIndicator(
    eventRegistrationState: EventRegistrationState,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        EventRegistrationStateProgressBar(eventRegistrationState.progress)
        EventRegistrationStateText(eventRegistrationState.page)
    }
}

@Composable
private fun EventRegistrationStateText(
    currentRegistrationPage: String,
) {
    Row {
        Text(
            text = currentRegistrationPage,
            style = WappTheme.typography.contentMedium,
            color = WappTheme.colors.yellow34,
        )
        Text(
            text = stringResource(R.string.event_registration_total_page),
            style = WappTheme.typography.contentMedium,
            color = WappTheme.colors.white,
        )
    }
}

@Composable
private fun EventRegistrationStateProgressBar(
    currentRegistrationProgress: Float,
) {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp),
        color = WappTheme.colors.yellow34,
        progress = currentRegistrationProgress,
        strokeCap = StrokeCap.Round,
    )
}