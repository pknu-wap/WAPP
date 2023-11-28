package com.wap.wapp.feature.management.survey

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.feature.management.R
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SurveyRegistrationScreen(
    viewModel: SurveyRegistrationViewModel = hiltViewModel(),
    onBackButtonClicked: () -> Unit,
) {
    val currentRegistrationState = viewModel.currentRegistrationState.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.surveyRegistrationEvent.collectLatest {
            when (it) {
                is SurveyRegistrationViewModel.SurveyRegistrationEvent.Failure -> {
                    snackBarHostState.showSnackbar(it.error.toSupportingText())
                }

                is SurveyRegistrationViewModel.SurveyRegistrationEvent.ValidationError -> {
                    snackBarHostState.showSnackbar(it.message)
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                WappTopBar(
                    titleRes = R.string.survey_registeration,
                    showLeftButton = true,
                    onClickLeftButton = { onBackButtonClicked() },
                )

                SurveyRegistrationStateIndicator(
                    surveyRegistrationState = currentRegistrationState,
                )
            }

            SurveyRegistrationContent(
                currentRegistrationState,
                viewModel,
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
private fun SurveyRegistrationContent(
    surveyRegistrationState: SurveyRegistrationState,
    viewModel: SurveyRegistrationViewModel,
) {
    val totalQuestionSize = viewModel.surveyQuestionList.collectAsState().value.size + 1

    when (surveyRegistrationState) {
        SurveyRegistrationState.INFORMATION -> {
            SurveyInformationContent(
                title = viewModel.surveyTitle.collectAsState().value,
                onTitleChange = { title -> viewModel.setSurveyTitle(title) },
                content = viewModel.surveyContent.collectAsState().value,
                onContentChange = { content -> viewModel.setSurveyContent(content) },
                onNextButtonClicked = {
                    if (viewModel.isValidSurveyInformation()) {
                        viewModel.setSurveyRegistrationState(SurveyRegistrationState.QUESTION)
                    }
                },
            )
        }

        SurveyRegistrationState.QUESTION -> {
            SurveyQuestionContent(
                question = viewModel.surveyQuestion.collectAsState().value,
                onQuestionChanged = { question -> viewModel.setSurveyQuestion(question) },
                onAddSurveyQuestionButtonClicked = { type ->
                    viewModel.addSurveyQuestion(type)
                },
                currentQuestionIndex = totalQuestionSize,
                totalQuestionIndex = totalQuestionSize,
                onNextButtonClicked = {
                    if (viewModel.isValidSurveyQuestion()) {
                        viewModel.setSurveyRegistrationState(SurveyRegistrationState.DEADLINE)
                    }
                },
            )
        }

        SurveyRegistrationState.DEADLINE -> {
            SurveyDeadlineContent(
                onRegisterButtonClicked = {
                    if (viewModel.isValidSurveyDeadline()) {
                        viewModel.registerSurvey()
                    }
                },
                time = viewModel.surveyTimeDeadline.collectAsState().value,
                date = viewModel.surveyDateDeadline.collectAsState().value,
                onDateChanged = { localDate ->
                    viewModel.setSurveyDateDeadline(localDate)
                },
                onTimeChanged = { localTime ->
                    viewModel.setSurveyTimeDeadline(localTime)
                },
            )
        }
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
            color = WappTheme.colors.yellow,
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
        color = WappTheme.colors.yellow,
        progress = currentRegistrationProgress,
        strokeCap = StrokeCap.Round,
    )
}
