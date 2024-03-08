package com.wap.wapp.feature.management.survey.registration

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappSubTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.designresource.R.drawable
import com.wap.wapp.feature.management.survey.R
import com.wap.wapp.feature.management.survey.SurveyFormContent
import com.wap.wapp.feature.management.survey.SurveyFormState
import com.wap.wapp.feature.management.survey.SurveyFormStateIndicator
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SurveyRegistrationScreen(
    viewModel: SurveyFormRegistrationViewModel = hiltViewModel(),
    navigateToManagement: () -> Unit,
) {
    val currentRegistrationState =
        viewModel.currentSurveyFormState.collectAsStateWithLifecycle().value
    val eventList = viewModel.eventList.collectAsStateWithLifecycle().value
    val eventSelection = viewModel.eventSelection.collectAsStateWithLifecycle().value
    val title = viewModel.surveyTitle.collectAsStateWithLifecycle().value
    val content = viewModel.surveyContent.collectAsStateWithLifecycle().value
    val questionTitle = viewModel.questionTitle.collectAsStateWithLifecycle().value
    val questionType = viewModel.questionType.collectAsStateWithLifecycle().value
    val currentQuestionNumber = viewModel.currentQuestionNumber.collectAsStateWithLifecycle().value
    val totalQuestionNumber =
        viewModel.totalQuestionNumber.collectAsStateWithLifecycle().value
    val questionList = viewModel.questionList.collectAsStateWithLifecycle().value
    val timeDeadline = viewModel.timeDeadline.collectAsStateWithLifecycle().value
    val dateDeadline = viewModel.dateDeadline.collectAsStateWithLifecycle().value
    val snackBarHostState = remember { SnackbarHostState() }
    val timePickerState = rememberTimePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.surveyRegistrationEvent.collectLatest {
            when (it) {
                is SurveyFormRegistrationViewModel.SurveyRegistrationEvent.Failure -> {
                    snackBarHostState.showSnackbar(it.error.toSupportingText())
                }

                is SurveyFormRegistrationViewModel.SurveyRegistrationEvent.ValidationError -> {
                    snackBarHostState.showSnackbar(it.message)
                }

                is SurveyFormRegistrationViewModel.SurveyRegistrationEvent.Success -> {
                    navigateToManagement()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = WappTheme.colors.backgroundBlack,
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            WappSubTopBar(
                titleRes = R.string.survey_registeration,
                showLeftButton = true,
                onClickLeftButton = navigateToManagement,
                leftButtonDrawableRes = drawable.ic_close,
                modifier = Modifier.padding(top = 16.dp),
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(vertical = 16.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp),
        ) {
            SurveyFormStateIndicator(surveyRegistrationState = currentRegistrationState)

            SurveyFormContent(
                surveyRegistrationState = currentRegistrationState,
                eventsState = eventList,
                eventSelection = eventSelection,
                title = title,
                content = content,
                questionTitle = questionTitle,
                questionType = questionType,
                dateDeadline = dateDeadline,
                timeDeadline = timeDeadline,
                timePickerState = timePickerState,
                showDatePicker = showDatePicker,
                showTimePicker = showTimePicker,
                currentQuestionNumber = currentQuestionNumber,
                totalQuestionNumber = totalQuestionNumber,
                onDatePickerStateChanged = { state -> showDatePicker = state },
                onTimePickerStateChanged = { state -> showTimePicker = state },
                onEventContentInitialized = { viewModel.getEventList() },
                onEventSelected = { event -> viewModel.setSurveyEventSelection(event) },
                onTitleChanged = { title -> viewModel.setSurveyTitle(title) },
                onContentChanged = { content -> viewModel.setSurveyContent(content) },
                onQuestionTitleChanged = { question -> viewModel.setSurveyQuestionTitle(question) },
                onQuestionTypeChanged = { questionType ->
                    viewModel.setSurveyQuestionType(questionType)
                },
                onNextQuestionButtonClicked = { // '>' 버튼
                    if (viewModel.validateSurveyForm(SurveyFormState.QUESTION).not()) {
                        return@SurveyFormContent
                    }

                    viewModel.editSurveyQuestion() // 답변 수정

                    viewModel.setNextQuestionNumber() // 다음 질문 불러오기
                    viewModel.setQuestion()
                },
                onPreviousQuestionButtonClicked = { // '<' 버튼
                    if (viewModel.validateSurveyForm(SurveyFormState.QUESTION).not()) {
                        return@SurveyFormContent
                    }

                    // 다른 질문으로 넘어가기 전, 질문 추가 혹은 저장
                    if (currentQuestionNumber == questionList.size) {
                        viewModel.addSurveyQuestion()
                    } else {
                        viewModel.editSurveyQuestion()
                    }

                    viewModel.setPreviousQuestionNumber() // 이전 질문 불러오기
                    viewModel.setQuestion()
                },
                onAddQuestionButtonClicked = { // 문항 추가 버튼
                    if (viewModel.validateSurveyForm(SurveyFormState.QUESTION).not()) {
                        return@SurveyFormContent
                    }

                    if (currentQuestionNumber == questionList.size) {
                        viewModel.addSurveyQuestion() // 질문 추가
                    } else {
                        viewModel.editSurveyQuestion()
                    }

                    viewModel.setLastQuestion()
                },
                onDeleteQuestionButtonClicked = {
                    viewModel.deleteSurveyQuestion()
                    viewModel.setQuestion()
                },
                onDateChanged = viewModel::setSurveyDateDeadline,
                onTimeChanged = { localTime -> viewModel.setSurveyTimeDeadline(localTime) },
                onPreviousButtonClicked = { previousState -> // 이전 버튼
                    if (previousState == SurveyFormState.QUESTION) {
                        viewModel.setSurveyQuestionFromQuestionList()
                    }

                    viewModel.setSurveyFormState(previousState)
                },
                onNextButtonClicked = { currentState, nextState -> // 다음 버튼
                    if (viewModel.validateSurveyForm(currentState).not()) {
                        return@SurveyFormContent
                    }

                    if (currentState == SurveyFormState.QUESTION) {
                        if (currentQuestionNumber == questionList.size) { // 마지막 질문인 경우
                            viewModel.addSurveyQuestion()
                        } else {
                            viewModel.editSurveyQuestion()
                        }
                    }

                    viewModel.setSurveyFormState(nextState)
                },
                onRegisterButtonClicked = {
                    if (viewModel.validateSurveyForm(SurveyFormState.DEADLINE)) {
                        viewModel.registerSurvey()
                    }
                },
            )
        }
    }
}
