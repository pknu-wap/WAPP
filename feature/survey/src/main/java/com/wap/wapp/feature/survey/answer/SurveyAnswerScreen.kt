package com.wap.wapp.feature.survey.answer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.designresource.R.drawable
import com.wap.wapp.core.model.survey.QuestionType
import com.wap.wapp.core.model.survey.Rating
import com.wap.wapp.core.model.survey.SurveyQuestion
import com.wap.wapp.feature.survey.R
import com.wap.wapp.feature.survey.answer.SurveyAnswerViewModel.SurveyFormUiState
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SurveyAnswerScreen(
    viewModel: SurveyAnswerViewModel,
    onSubmitButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
    eventId: Int,
) {
    val surveyFormUiState = viewModel.surveyFormUiState.collectAsStateWithLifecycle().value
    val questionNumber = viewModel.questionNumber.collectAsStateWithLifecycle().value
    val subjectiveAnswer = viewModel.subjectiveAnswer.collectAsStateWithLifecycle().value
    val objectiveAnswer = viewModel.objectiveAnswer.collectAsStateWithLifecycle().value
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.getSurveyForm(eventId)

        viewModel.surveyAnswerEvent.collectLatest {
            when (it) {
                is SurveyAnswerViewModel.SurveyAnswerUiEvent.SubmitSuccess -> {
                    onSubmitButtonClicked()
                }

                is SurveyAnswerViewModel.SurveyAnswerUiEvent.Failure -> {
                    snackBarHostState.showSnackbar(it.throwable.toSupportingText())
                }
            }
        }
    }

    Scaffold(
        topBar = {
            SurveyAnswerTopBar(onBackButtonClicked = onBackButtonClicked)
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = WappTheme.colors.backgroundBlack,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            when (surveyFormUiState) {
                is SurveyFormUiState.Init -> {}

                is SurveyFormUiState.Success -> {
                    val questionList = surveyFormUiState.surveyForm.surveyQuestionList
                    val surveyQuestion = questionList[questionNumber]
                    val lastQuestionNumber = questionList.lastIndex

                    SurveyAnswerForm(
                        surveyQuestion = surveyQuestion,
                        questionNumber = questionNumber,
                        lastQuestionNumber = lastQuestionNumber,
                        subjectiveAnswer = subjectiveAnswer,
                        objectiveAnswer = objectiveAnswer,
                        onSubjectiveAnswerChanged = viewModel::setSubjectiveAnswer,
                        onObjectiveAnswerSelected = viewModel::setObjectiveAnswer,
                        onNextButtonClicked = {
                            viewModel.addSurveyAnswer()
                            viewModel.setNextQuestion()
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun SurveyAnswerForm(
    surveyQuestion: SurveyQuestion,
    questionNumber: Int,
    lastQuestionNumber: Int,
    subjectiveAnswer: String,
    objectiveAnswer: Rating,
    onSubjectiveAnswerChanged: (String) -> Unit,
    onObjectiveAnswerSelected: (Rating) -> Unit,
    onNextButtonClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize(),
    ) {
        SurveyAnswerStateIndicator(
            index = questionNumber + 1,
            size = lastQuestionNumber + 1,
        )

        when (surveyQuestion.questionType) {
            QuestionType.SUBJECTIVE -> {
                SubjectiveSurveyForm(
                    questionTitle = surveyQuestion.questionTitle,
                    answer = subjectiveAnswer,
                    onAnswerChanged = onSubjectiveAnswerChanged,
                )
            }

            QuestionType.OBJECTIVE -> {
                ObjectiveSurveyForm(
                    questionTitle = surveyQuestion.questionTitle,
                    answer = objectiveAnswer,
                    onAnswerSelected = onObjectiveAnswerSelected,
                )
            }
        }

        val isLastQuestion = questionNumber == lastQuestionNumber // 마지막 응답일 경우, 완료로 변경
        SurveyAnswerButton(
            isLastQuestion = isLastQuestion,
            onButtonClicked = onNextButtonClicked,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SurveyAnswerTopBar(
    onBackButtonClicked: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.survey_answer),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = WappTheme.typography.contentBold,
                color = WappTheme.colors.white,
            )
        },
        navigationIcon = {
            Icon(
                painter = painterResource(id = drawable.ic_back),
                contentDescription = stringResource(
                    id = com.wap.wapp.core.designsystem.R.string.back_button,
                ),
                tint = WappTheme.colors.white,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable { onBackButtonClicked() },
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = WappTheme.colors.backgroundBlack,
        ),
    )
}

@Composable
private fun SurveyAnswerButton(
    isLastQuestion: Boolean,
    onButtonClicked: () -> Unit,
) {
    if (isLastQuestion) {
        WappButton(
            textRes = R.string.submit,
            onClick = { onButtonClicked() },
        )
    } else {
        WappButton(
            textRes = R.string.next,
            onClick = { onButtonClicked() },
        )
    }
}
