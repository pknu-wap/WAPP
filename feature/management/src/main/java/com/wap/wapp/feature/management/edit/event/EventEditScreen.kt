package com.wap.wapp.feature.management.edit.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
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
import com.wap.wapp.feature.management.registration.event.EventRegistrationContent
import com.wap.wapp.feature.management.registration.event.EventRegistrationEvent
import com.wap.wapp.feature.management.registration.event.EventRegistrationState
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalTime

@Composable
internal fun EventEditRoute(
    date: String,
    eventId: String,
    navigateToManagement: () -> Unit,
    viewModel: EventEditViewModel = hiltViewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val currentRegistrationState
        by viewModel.currentEditState.collectAsStateWithLifecycle()
    val title by viewModel.eventTitle.collectAsStateWithLifecycle()
    val content by viewModel.eventContent.collectAsStateWithLifecycle()
    val location by viewModel.eventLocation.collectAsStateWithLifecycle()
    val startDate by viewModel.eventStartDate.collectAsStateWithLifecycle()
    val startTime by viewModel.eventStartTime.collectAsStateWithLifecycle()
    val endDate by viewModel.eventEndDate.collectAsStateWithLifecycle()
    val endTime by viewModel.eventEndTime.collectAsStateWithLifecycle()
    val onTitleChanged = viewModel::setEventTitle
    val onContentChanged = viewModel::setEventContent
    val onLocationChanged = viewModel::setEventLocation
    val onStartDateChanged = viewModel::setEventStartDate
    val onStartTimeChanged = viewModel::setEventStartTime
    val onEndDateChanged = viewModel::setEventEndDate
    val onEndTimeChanged = viewModel::setEventEndTime
    val onNextButtonClicked =
        viewModel::setEventRegistrationState
    val onRegisterButtonClicked = viewModel::updateEvent

    LaunchedEffect(true) {
        viewModel.getEvent(date = date, eventId = eventId)

        viewModel.eventEditEvent.collectLatest {
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

    EventEditScreen(
        currentEditState = currentRegistrationState,
        title = title,
        content = content,
        location = location,
        startDate = startDate,
        startTime = startTime,
        endDate = endDate,
        endTime = endTime,
        snackBarHostState = snackBarHostState,
        onTitleChanged = onTitleChanged,
        onContentChanged = onContentChanged,
        onLocationChanged = onLocationChanged,
        onStartDateChanged = onStartDateChanged,
        onStartTimeChanged = onStartTimeChanged,
        onEndDateChanged = onEndDateChanged,
        onEndTimeChanged = onEndTimeChanged,
        onNextButtonClicked = onNextButtonClicked,
        onEditButtonClicked = onRegisterButtonClicked,
        onBackButtonClicked = navigateToManagement,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventEditScreen(
    currentEditState: EventRegistrationState,
    title: String,
    content: String,
    location: String,
    startDate: LocalDate,
    startTime: LocalTime,
    endDate: LocalDate,
    endTime: LocalTime,
    snackBarHostState: SnackbarHostState,
    onTitleChanged: (String) -> Unit,
    onContentChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onStartDateChanged: (LocalDate) -> Unit,
    onStartTimeChanged: (LocalTime) -> Unit,
    onEndDateChanged: (LocalDate) -> Unit,
    onEndTimeChanged: (LocalTime) -> Unit,
    onNextButtonClicked: () -> Unit,
    onEditButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
) {
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = WappTheme.colors.backgroundBlack,
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0.dp),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            WappTopBar(
                titleRes = R.string.event_edit,
                showLeftButton = true,
                onClickLeftButton = onBackButtonClicked,
            )

            EventEditStateIndicator(
                eventRegistrationState = currentEditState,
                modifier = Modifier.padding(top = 16.dp),
            )

            EventRegistrationContent(
                eventRegistrationState = currentEditState,
                modifier = Modifier.padding(top = 50.dp),
                eventTitle = title,
                eventContent = content,
                location = location,
                startDate = startDate,
                startTime = startTime,
                endDate = endDate,
                endTime = endTime,
                showStartDatePicker = showStartDatePicker,
                showStartTimePicker = showStartTimePicker,
                showEndDatePicker = showEndDatePicker,
                showEndTimePicker = showEndTimePicker,
                onTitleChanged = onTitleChanged,
                onContentChanged = onContentChanged,
                onLocationChanged = onLocationChanged,
                timePickerState = timePickerState,
                onStartDateChanged = onStartDateChanged,
                onStartTimeChanged = onStartTimeChanged,
                onEndDateChanged = onEndDateChanged,
                onEndTimeChanged = onEndTimeChanged,
                onStartDatePickerStateChanged = { state -> showStartDatePicker = state },
                onStartTimePickerStateChanged = { state -> showStartTimePicker = state },
                onEndDatePickerStateChanged = { state -> showEndDatePicker = state },
                onEndTimePickerStateChanged = { state -> showEndTimePicker = state },
                onNextButtonClicked = onNextButtonClicked,
                onRegisterButtonClicked = onEditButtonClicked,
            )
        }
    }
}

@Composable
private fun EventEditStateIndicator(
    eventRegistrationState: EventRegistrationState,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        EventEditStateProgressBar(eventRegistrationState.progress)
        EventEditStateText(eventRegistrationState.page)
    }
}

@Composable
private fun EventEditStateText(
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
private fun EventEditStateProgressBar(
    currentRegistrationProgress: Float,
) {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp),
        color = WappTheme.colors.yellow34,
        trackColor = WappTheme.colors.white,
        progress = currentRegistrationProgress,
        strokeCap = StrokeCap.Round,
    )
}
