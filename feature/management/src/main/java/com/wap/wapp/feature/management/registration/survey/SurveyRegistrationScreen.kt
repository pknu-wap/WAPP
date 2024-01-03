package com.wap.wapp.feature.management.registration.survey

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
import androidx.compose.runtime.collectAsState
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
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.core.model.survey.QuestionType
import com.wap.wapp.feature.management.R
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalTime

@Composable
internal fun SurveyRegistrationRoute(
    viewModel: SurveyRegistrationViewModel = hiltViewModel(),
    navigateToManagement: () -> Unit,
) {
    val currentRegistrationState = viewModel.currentRegistrationState.collectAsState().value
    val eventList = viewModel.eventList.collectAsState().value
    val eventSelection = viewModel.surveyEventSelection.collectAsState().value
    val title = viewModel.surveyTitle.collectAsState().value
    val content = viewModel.surveyContent.collectAsState().value
    val question = viewModel.surveyQuestion.collectAsState().value
    val questionType = viewModel.surveyQuestionType.collectAsState().value
    val totalQuestionSize = viewModel.surveyQuestionList.collectAsState().value.size + 1
    val time = viewModel.surveyTimeDeadline.collectAsState().value
    val date = viewModel.surveyDateDeadline.collectAsState().value

    SurveyRegistrationScreen(
        currentRegistrationState = currentRegistrationState,
        eventList = eventList,
        eventSelection = eventSelection,
        title = title,
        content = content,
        question = question,
        questionType = questionType,
        totalQuestionSize = totalQuestionSize,
        time = time,
        date = date,
        onBackButtonClicked = { navigateToManagement() },
        viewModel = viewModel,
        registerSurveyForm = {
            navigateToManagement()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SurveyRegistrationScreen(
    currentRegistrationState: SurveyRegistrationState,
    eventList: List<Event>,
    eventSelection: Event,
    title: String,
    content: String,
    question: String,
    questionType: QuestionType,
    totalQuestionSize: Int,
    time: LocalTime,
    date: LocalDate,
    registerSurveyForm: () -> Unit,
    viewModel: SurveyRegistrationViewModel,
    onBackButtonClicked: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val timePickerState = rememberTimePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.surveyRegistrationEvent.collectLatest {
            when (it) {
                is SurveyRegistrationViewModel.SurveyRegistrationEvent.Failure -> {
                    snackBarHostState.showSnackbar(it.error.toSupportingText())
                }

                is SurveyRegistrationViewModel.SurveyRegistrationEvent.ValidationError -> {
                    snackBarHostState.showSnackbar(it.message)
                }

                is SurveyRegistrationViewModel.SurveyRegistrationEvent.Success -> {
                    registerSurveyForm()
                }
            }
        }
    }

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
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                WappTopBar(
                    titleRes = R.string.survey_registeration,
                    showLeftButton = true,
                    onClickLeftButton = onBackButtonClicked,
                )

                SurveyRegistrationStateIndicator(
                    surveyRegistrationState = currentRegistrationState,
                )
            }

            SurveyRegistrationContent(
                surveyRegistrationState = currentRegistrationState,
                eventList = eventList,
                eventSelection = eventSelection,
                title = title,
                content = content,
                question = question,
                questionType = questionType,
                date = date,
                time = time,
                timePickerState = timePickerState,
                showDatePicker = showDatePicker,
                showTimePicker = showTimePicker,
                currentQuestionIndex = totalQuestionSize,
                totalQuestionSize = totalQuestionSize,
                onDatePickerStateChanged = { state -> showDatePicker = state },
                onTimePickerStateChanged = { state -> showTimePicker = state },
                onEventListChanged = { viewModel.getEventList() },
                onEventSelected = { event -> viewModel.setSurveyEventSelection(event) },
                onTitleChanged = { title -> viewModel.setSurveyTitle(title) },
                onContentChanged = { content -> viewModel.setSurveyContent(content) },
                onQuestionChanged = { question -> viewModel.setSurveyQuestion(question) },
                onQuestionTypeChanged = { questionType ->
                    viewModel.setSurveyQuestionType(questionType)
                },
                onDateChanged = viewModel::setSurveyDateDeadline,
                onTimeChanged = { localTime -> viewModel.setSurveyTimeDeadline(localTime) },
                onNextButtonClicked = { surveyRegistrationState ->
                    viewModel.setSurveyRegistrationState(surveyRegistrationState)
                },
                onAddQuestionButtonClicked = { viewModel.addSurveyQuestion() },
                onRegisterButtonClicked = { viewModel.registerSurvey() },
            )
        }
    }
}

@Composable
private fun SurveyRegistrationStateIndicator(
    surveyRegistrationState: SurveyRegistrationState,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SurveyRegistrationStateProgressBar(surveyRegistrationState.progress)
        SurveyRegistrationStateText(surveyRegistrationState.page)
    }
}

@Composable
private fun SurveyRegistrationStateText(
    currentRegistrationPage: String,
) {
    Row {
        Text(
            text = currentRegistrationPage,
            style = WappTheme.typography.contentMedium,
            color = WappTheme.colors.yellow34,
        )
        Text(
            text = stringResource(R.string.survey_registration_total_page),
            style = WappTheme.typography.contentMedium,
            color = WappTheme.colors.white,
        )
    }
}

@Composable
private fun SurveyRegistrationStateProgressBar(
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
