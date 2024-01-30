package com.wap.wapp.feature.survey.answer

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappSubTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.model.survey.Rating
import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.feature.survey.R
import com.wap.wapp.feature.survey.answer.SurveyAnswerViewModel.SurveyFormUiState
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SurveyAnswerScreen(
    viewModel: SurveyAnswerViewModel,
    navigateToSurvey: () -> Unit,
    surveyFormId: String,
) {
    val surveyAnswerState = viewModel.surveyAnswerState.collectAsStateWithLifecycle().value
    val surveyFormUiState = viewModel.surveyFormUiState.collectAsStateWithLifecycle().value
    val surveyAnswerList = viewModel.surveyAnswerList.collectAsStateWithLifecycle().value
    val eventName = viewModel.eventName.collectAsStateWithLifecycle().value
    val questionNumber = viewModel.questionNumber.collectAsStateWithLifecycle().value
    val subjectiveAnswer = viewModel.subjectiveAnswer.collectAsStateWithLifecycle().value
    val objectiveAnswer = viewModel.objectiveAnswer.collectAsStateWithLifecycle().value
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.getSurveyForm(surveyFormId)

        viewModel.surveyAnswerEvent.collectLatest {
            when (it) {
                is SurveyAnswerViewModel.SurveyAnswerUiEvent.SubmitSuccess -> {
                    navigateToSurvey()
                }

                is SurveyAnswerViewModel.SurveyAnswerUiEvent.Failure -> {
                    snackBarHostState.showSnackbar(it.throwable.toSupportingText())
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = WappTheme.colors.backgroundBlack,
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            WappSubTopBar(
                titleRes = R.string.survey_answer,
                showLeftButton = true,
                modifier = Modifier.padding(top = 16.dp), // 하단은 Content Padding에 의존
                onClickLeftButton = {
                    when (surveyAnswerState) {
                        SurveyAnswerState.SURVEY_OVERVIEW -> navigateToSurvey()
                        SurveyAnswerState.SURVEY_ANSWER -> viewModel.setSurveyAnswerState(
                            SurveyAnswerState.SURVEY_OVERVIEW,
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        when (surveyFormUiState) {
            is SurveyFormUiState.Init -> {}

            is SurveyFormUiState.Success -> {
                SurveyAnswerContent(
                    surveyAnswerState = surveyAnswerState,
                    surveyForm = surveyFormUiState.surveyForm,
                    eventName = eventName,
                    questionNumber = questionNumber,
                    subjectiveAnswer = subjectiveAnswer,
                    objectiveAnswer = objectiveAnswer,
                    onSubjectiveAnswerChanged = viewModel::setSubjectiveAnswer,
                    onObjectiveAnswerSelected = viewModel::setObjectiveAnswer,
                    onStartSurveyButtonClicked = {
                        viewModel.setSurveyAnswerState(SurveyAnswerState.SURVEY_ANSWER)
                    },
                    onNextQuestionButtonClicked = {
                        if (questionNumber < surveyAnswerList.size) { // 작성한 답변을 수정하는 경우
                            viewModel.editSurveyAnswer()
                        } else {
                            viewModel.addSurveyAnswer()
                        }

                        val lastQuestionNumber =
                            surveyFormUiState.surveyForm.surveyQuestionList.lastIndex
                        if (questionNumber == lastQuestionNumber) { // 마지막 질문에 답변을 한 경우
                            viewModel.submitSurvey()
                            return@SurveyAnswerContent
                        }

                        viewModel.setNextQuestionAndAnswer() // 다음 질문 불러오기
                    },
                    onPreviousQuestionButtonClicked = {
                        val lastQuestionNumber =
                            surveyFormUiState.surveyForm.surveyQuestionList.lastIndex
                        // 마지막 질문이면서, 응답의 갯수가 질문의 갯수보다 작은 경우
                        if (questionNumber == lastQuestionNumber &&
                            surveyAnswerList.lastIndex < lastQuestionNumber
                        ) {
                            viewModel.addSurveyAnswer()
                        }

                        viewModel.setPreviousQuestionAndAnswer()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                )
            }
        }
    }
}

@Composable
private fun SurveyAnswerContent(
    surveyAnswerState: SurveyAnswerState,
    surveyForm: SurveyForm,
    eventName: String,
    questionNumber: Int, // 전체 질문 중, 현재 질문 번호
    subjectiveAnswer: String,
    objectiveAnswer: Rating,
    onSubjectiveAnswerChanged: (String) -> Unit,
    onObjectiveAnswerSelected: (Rating) -> Unit,
    onStartSurveyButtonClicked: () -> Unit,
    onNextQuestionButtonClicked: () -> Unit,
    onPreviousQuestionButtonClicked: () -> Unit,
    modifier: Modifier,
) {
    when (surveyAnswerState) {
        SurveyAnswerState.SURVEY_OVERVIEW -> {
            SurveyOverview(
                surveyForm = surveyForm,
                modifier = modifier.padding(top = 16.dp),
                eventName = eventName,
                onStartSurveyButtonClicked = onStartSurveyButtonClicked,
            )
        }

        SurveyAnswerState.SURVEY_ANSWER -> {
            SurveyAnswerForm(
                surveyForm = surveyForm,
                modifier = modifier,
                questionNumber = questionNumber,
                subjectiveAnswer = subjectiveAnswer,
                objectiveAnswer = objectiveAnswer,
                onSubjectiveAnswerChanged = onSubjectiveAnswerChanged,
                onObjectiveAnswerSelected = onObjectiveAnswerSelected,
                onNextQuestionButtonClicked = onNextQuestionButtonClicked,
                onPreviousQuestionButtonClicked = onPreviousQuestionButtonClicked,
            )
        }
    }
}
