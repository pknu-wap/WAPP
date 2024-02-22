package com.wap.wapp.feature.management.event.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappSubTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.designresource.R.drawable
import com.wap.wapp.feature.management.event.R
import com.wap.wapp.feature.management.event.edit.EventEditViewModel.EventEditEvent
import com.wap.wapp.feature.management.event.registration.EventRegistrationContent
import com.wap.wapp.feature.management.event.registration.EventRegistrationState
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalTime

@Composable
internal fun EventEditRoute(
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
        viewModel.getEvent(eventId = eventId)

        viewModel.eventEditEvent.collectLatest {
            when (it) {
                is EventEditEvent.Failure ->
                    snackBarHostState.showSnackbar(it.error.toSupportingText())

                is EventEditEvent.ValidationError ->
                    snackBarHostState.showSnackbar(it.message)

                is EventEditEvent.EditSuccess ->
                    navigateToManagement()

                is EventEditEvent.DeleteSuccess ->
                    navigateToManagement()
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
        deleteEvent = viewModel::deleteEvent,
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
    deleteEvent: () -> Unit,
) {
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }
    var showDeleteEventDialog by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState()

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = WappTheme.colors.backgroundBlack,
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0.dp),
    ) { paddingValues ->

        if (showDeleteEventDialog) {
            DeleteEventDialog(
                deleteEvent = deleteEvent,
                onDismissRequest = { showDeleteEventDialog = false },
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(vertical = 16.dp),
        ) {
            WappSubTopBar(
                titleRes = R.string.event_edit,
                showLeftButton = true,
                showRightButton = true,
                leftButtonDrawableRes = drawable.ic_close,
                onClickLeftButton = onBackButtonClicked,
                onClickRightButton = { showDeleteEventDialog = true },
            )

            EventEditStateIndicator(
                eventRegistrationState = currentEditState,
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 20.dp),
            )

            EventRegistrationContent(
                eventRegistrationState = currentEditState,
                modifier = Modifier.padding(horizontal = 20.dp),
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

@Composable
private fun DeleteEventDialog(
    deleteEvent: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 30.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(WappTheme.colors.black25),
        ) {
            Text(
                text = stringResource(id = R.string.delete_event),
                style = WappTheme.typography.contentBold.copy(fontSize = 20.sp),
                color = WappTheme.colors.yellow34,
                modifier = Modifier.padding(top = 16.dp),
            )

            Divider(
                color = WappTheme.colors.gray82,
                modifier = Modifier.padding(horizontal = 12.dp),
            )

            Text(
                text = generateDialogContentString(),
                style = WappTheme.typography.contentRegular,
                color = WappTheme.colors.white,
                modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp),
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
            ) {
                Button(
                    onClick = {
                        deleteEvent()
                        onDismissRequest()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WappTheme.colors.yellow34,
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = stringResource(id = R.string.complete),
                        style = WappTheme.typography.titleRegular,
                        color = WappTheme.colors.black,
                    )
                }

                Button(
                    onClick = onDismissRequest,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WappTheme.colors.black25,
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = WappTheme.colors.yellow34,
                            shape = RoundedCornerShape(8.dp),
                        ),
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = WappTheme.typography.titleRegular,
                        color = WappTheme.colors.yellow34,
                    )
                }
            }
        }
    }
}

@Composable
private fun generateDialogContentString() = buildAnnotatedString {
    append("정말로 해당 일정을 삭제하시겠습니까?\n")
    withStyle(
        style = SpanStyle(
            textDecoration = TextDecoration.Underline,
            color = WappTheme.colors.yellow34,
        ),
    ) {
        append("해당 일정과 관련된 설문과 답변들이 모두 삭제됩니다.")
    }
}
