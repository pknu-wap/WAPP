package com.wap.wapp.feature.management.registration.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.feature.management.R
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventRegistrationRoute(
    viewModel: EventRegistrationViewModel = hiltViewModel(),
    navigateToManagement: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val currentRegistrationState
        by viewModel.currentRegistrationState.collectAsStateWithLifecycle()
    val title by viewModel.eventTitle.collectAsStateWithLifecycle()
    val content by viewModel.eventContent.collectAsStateWithLifecycle()
    val location by viewModel.eventLocation.collectAsStateWithLifecycle()
    val date by viewModel.eventDate.collectAsStateWithLifecycle()
    val time by viewModel.eventTime.collectAsStateWithLifecycle()
    val onTitleChanged = viewModel::setEventTitle
    val onContentChanged = viewModel::setEventContent
    val onLocationChanged = viewModel::setEventLocation
    val onDateChanged = viewModel::setEventDate
    val onTimeChanged = viewModel::setEventTime
    val onNextButtonClicked =
        viewModel::setEventRegistrationState
    val onRegisterButtonClicked = viewModel::registerEvent

    LaunchedEffect(true) {
        viewModel.eventRegistrationEvent.collectLatest {
            when (it) {
                is EventRegistrationEvent.Failure -> {
                    snackBarHostState.showSnackbar(it.error.toSupportingText())
                }

                is EventRegistrationEvent.ValidationError -> {
                    snackBarHostState.showSnackbar(it.message)
                }

                is EventRegistrationEvent.Success -> {
                    navigateToManagement()
                }
            }
        }
    }

    EventRegistrationScreen(
        currentRegistrationState = currentRegistrationState,
        title = title,
        content = content,
        location = location,
        date = date,
        time = time,
        snackBarHostState = snackBarHostState,
        onTitleChanged = onTitleChanged,
        onContentChanged = onContentChanged,
        onLocationChanged = onLocationChanged,
        onDateChanged = onDateChanged,
        onTimeChanged = onTimeChanged,
        onNextButtonClicked = onNextButtonClicked,
        onRegisterButtonClicked = onRegisterButtonClicked,
        onBackButtonClicked = navigateToManagement,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventRegistrationScreen(
    currentRegistrationState: EventRegistrationState,
    title: String,
    content: String,
    location: String,
    date: LocalDate,
    time: LocalTime,
    snackBarHostState: SnackbarHostState,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onDateChanged: (LocalDate) -> Unit,
    onTimeChanged: (LocalTime) -> Unit,
    onNextButtonClicked: () -> Unit,
    onRegisterButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

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
                eventTitle = title,
                eventContent = content,
                location = location,
                date = date,
                time = time,
                showDatePicker = showDatePicker,
                showTimePicker = showTimePicker,
                onTitleChanged = onTitleChanged,
                onContentChanged = onContentChanged,
                onLocationChanged = onLocationChanged,
                timePickerState = timePickerState,
                onDateChanged = onDateChanged,
                onTimeChanged = onTimeChanged,
                onDatePickerStateChanged = { state -> showDatePicker = state },
                onTimePickerStateChanged = { state -> showTimePicker = state },
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
