package com.wap.wapp.feature.survey.check

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.model.survey.QuestionType
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SurveyCheckRoute(
    viewModel: SurveyCheckViewModel = hiltViewModel(),
    surveyId: String,
    navigateToManagement: () -> Unit,
) {
    val surveyUiState by viewModel.surveyUiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.getSurvey(surveyId)
    }

    LaunchedEffect(true) {
        viewModel.surveyCheckUiEvent.collectLatest {
            when (it) {
                is SurveyCheckViewModel.SurveyUiEvent.Failure -> {
                    snackBarHostState.showSnackbar(it.throwable.toSupportingText())
                }
            }
        }
    }

    SurveyCheckScreen(
        snackBarHostState = snackBarHostState,
        surveyUiState = surveyUiState,
        onDoneButtonClicked = { navigateToManagement() },
        onBackButtonClicked = { navigateToManagement() },
    )
}

@Composable
internal fun SurveyCheckScreen(
    snackBarHostState: SnackbarHostState,
    surveyUiState: SurveyCheckViewModel.SurveyUiState,
    onDoneButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            SurveyCheckTopBar(onBackButtonClicked = onBackButtonClicked)
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = WappTheme.colors.backgroundBlack,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            when (surveyUiState) {
                is SurveyCheckViewModel.SurveyUiState.Init -> {}

                is SurveyCheckViewModel.SurveyUiState.Success -> {
                    SurveyInformationCard(
                        title = surveyUiState.survey.title,
                        content = surveyUiState.survey.content,
                        userName = surveyUiState.survey.userName,
                        eventName = surveyUiState.survey.eventName,
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(32.dp),
                    ) {
                        surveyUiState.survey.surveyAnswerList.forEach { surveyAnswer ->
                            when (surveyAnswer.questionType) {
                                QuestionType.OBJECTIVE -> {
                                    ObjectiveQuestionCard(surveyAnswer)
                                }

                                QuestionType.SUBJECTIVE -> {
                                    SubjectiveQuestionCard(surveyAnswer)
                                }
                            }
                        }
                    }

                    WappButton(
                        onClick = onDoneButtonClicked,
                        textRes = com.wap.wapp.core.designsystem.R.string.done,
                    )
                }
            }
        }
    }
}
