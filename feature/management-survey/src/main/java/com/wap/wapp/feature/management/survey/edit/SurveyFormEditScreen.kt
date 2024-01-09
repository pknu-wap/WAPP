package com.wap.wapp.feature.management.survey.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappSubTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.feature.management.survey.R
import com.wap.wapp.feature.management.survey.SurveyFormContent
import com.wap.wapp.feature.management.survey.SurveyFormState
import com.wap.wapp.feature.management.survey.SurveyFormStateIndicator
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SurveyFormEditScreen(
    viewModel: SurveyFormEditViewModel = hiltViewModel(),
    surveyFormId: String,
    navigateToManagement: () -> Unit,
) {
    val currentRegistrationState = viewModel.currentSurveyFormState.collectAsState().value
    val eventList = viewModel.eventList.collectAsState().value
    val eventSelection = viewModel.surveyEventSelection.collectAsState().value
    val title = viewModel.surveyTitle.collectAsState().value
    val content = viewModel.surveyContent.collectAsState().value
    val question = viewModel.surveyQuestion.collectAsState().value
    val questionType = viewModel.surveyQuestionType.collectAsState().value
    val totalQuestionSize = viewModel.surveyQuestionList.collectAsState().value.size + 1
    val time = viewModel.surveyTimeDeadline.collectAsState().value
    val date = viewModel.surveyDateDeadline.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }
    val timePickerState = rememberTimePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.getSurveyForm(surveyFormId)
    }

    LaunchedEffect(true) {
        viewModel.surveyFormEditUiEvent.collectLatest { surveyFormEditEvent ->
            when (surveyFormEditEvent) {
                is SurveyFormEditViewModel.SurveyFormEditUiEvent.Success -> {
                    navigateToManagement()
                }
                is SurveyFormEditViewModel.SurveyFormEditUiEvent.Failure -> {
                    snackBarHostState.showSnackbar(surveyFormEditEvent.throwable.toSupportingText())
                }
                is SurveyFormEditViewModel.SurveyFormEditUiEvent.ValidationError -> {
                    snackBarHostState.showSnackbar(surveyFormEditEvent.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = WappTheme.colors.backgroundBlack,
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0.dp),
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
                WappSubTopBar(
                    titleRes = R.string.survey_edit,
                    showLeftButton = true,
                    onClickLeftButton = navigateToManagement,
                )

                SurveyFormStateIndicator(
                    surveyRegistrationState = currentRegistrationState,
                )
            }

            SurveyFormContent(
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
                onNextButtonClicked = { currentState, nextState ->
                    if (viewModel.validateSurveyForm(currentState).not()) { // 유효성 검증
                        return@SurveyFormContent
                    }

                    if (currentState == SurveyFormState.QUESTION) {
                        viewModel.addSurveyQuestion() // 마지막으로 작성한 질문, 질문 목록에 추가
                    }

                    viewModel.setSurveyFormState(nextState) // 다음 단계
                },
                onAddQuestionButtonClicked = {
                    if (viewModel.validateSurveyForm(SurveyFormState.QUESTION)) {
                        viewModel.addSurveyQuestion()
                    }
                },
                onRegisterButtonClicked = {
                    if (viewModel.validateSurveyForm(SurveyFormState.DEADLINE)) {
                        viewModel.updateSurveyForm()
                    }
                },
            )
        }
    }
}
