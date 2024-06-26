package com.wap.wapp.feature.survey.check.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
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
import com.wap.designsystem.component.CircleLoader
import com.wap.designsystem.component.WappButton
import com.wap.wapp.core.commmon.extensions.TrackScreenViewEvent
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.model.survey.QuestionType
import com.wap.wapp.feature.survey.check.navigation.SurveyDetailBackStack
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SurveyDetailRoute(
    viewModel: SurveyDetailViewModel = hiltViewModel(),
    surveyId: String,
    backStack: String,
    navigateToSurveyCheck: () -> Unit,
    navigateToProfile: () -> Unit,
) {
    val surveyUiState by viewModel.surveyUiState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val navigateToPrevPage = {
        if (backStack == SurveyDetailBackStack.SURVEY_CHECK.name) {
            navigateToSurveyCheck()
        } else {
            navigateToProfile()
        }
    }

    LaunchedEffect(true) {
        viewModel.getSurvey(surveyId)
    }

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest {
            snackBarHostState.showSnackbar(it.toSupportingText())
        }
    }

    TrackScreenViewEvent(screenName = "SurveyDetailScreen")

    SurveyDetailScreen(
        snackBarHostState = snackBarHostState,
        surveyUiState = surveyUiState,
        onDoneButtonClicked = navigateToPrevPage,
        onBackButtonClicked = navigateToPrevPage,
    )
}

@Composable
internal fun SurveyDetailScreen(
    snackBarHostState: SnackbarHostState,
    surveyUiState: SurveyDetailViewModel.SurveyUiState,
    onDoneButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            SurveyDetailTopBar(
                onBackButtonClicked = onBackButtonClicked,
            )
        },
        contentWindowInsets = WindowInsets(0.dp),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = WappTheme.colors.backgroundBlack,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(top = 16.dp, start = 8.dp, end = 8.dp),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                when (surveyUiState) {
                    is SurveyDetailViewModel.SurveyUiState.Loading -> {
                        Spacer(modifier = Modifier.weight(1f))

                        CircleLoader(modifier = Modifier.fillMaxSize())

                        Spacer(modifier = Modifier.weight(1f))
                    }

                    is SurveyDetailViewModel.SurveyUiState.Success -> {
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
                                    QuestionType.OBJECTIVE -> ObjectiveQuestionCard(surveyAnswer)

                                    QuestionType.SUBJECTIVE -> SubjectiveQuestionCard(surveyAnswer)
                                }
                            }
                        }
                    }
                }
            }

            WappButton(
                onClick = onDoneButtonClicked,
                textRes = com.wap.wapp.core.designsystem.R.string.done,
                modifier = Modifier.padding(vertical = 20.dp),
            )
        }
    }
}
