package com.wap.wapp.feature.management.check

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappButton
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.designresource.R.drawable
import com.wap.wapp.core.model.survey.QuestionType
import com.wap.wapp.core.model.survey.Rating
import com.wap.wapp.core.model.survey.SurveyAnswer
import com.wap.wapp.core.model.survey.toDescription
import com.wap.wapp.feature.management.R
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SurveyCheckRoute(
    viewModel: SurveyCheckViewModel = hiltViewModel(),
    surveyId: String = "",
    navigateToManagement: () -> Unit,
) {
    val surveyUiState by viewModel.surveyUiState.collectAsStateWithLifecycle()

    SurveyCheckScreen(
        surveyId = surveyId,
        surveyUiState = surveyUiState,
        onDoneButtonClicked = { navigateToManagement() },
        onBackButtonClicked = { navigateToManagement() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SurveyCheckScreen(
    viewModel: SurveyCheckViewModel = hiltViewModel(),
    surveyId: String,
    surveyUiState: SurveyCheckViewModel.SurveyUiState,
    onDoneButtonClicked: () -> Unit,
    onBackButtonClicked: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()

    LaunchedEffect(true) {
        viewModel.getSurvey(surveyId)

        viewModel.surveyCheckUiEvent.collectLatest {
            when (it) {
                is SurveyCheckViewModel.SurveyUiEvent.Failure -> {
                    snackBarHostState.showSnackbar(it.throwable.toSupportingText())
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.check_survey),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = WappTheme.typography.contentBold,
                    )
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = drawable.ic_back),
                        contentDescription = stringResource(
                            id = com.wap.wapp.core.designsystem.R.string.back_button,
                        ),
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .clickable { onBackButtonClicked() },
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WappTheme.colors.yellow34,
                ),
            )
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
                        surveyUiState.survey.answerList.forEach { surveyAnswer ->
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

@Composable
private fun SurveyInformationCard(
    title: String,
    content: String,
    userName: String,
    eventName: String,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = WappTheme.colors.black25,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column {
                Text(
                    text = title,
                    style = WappTheme.typography.titleBold,
                    fontSize = 22.sp,
                    color = WappTheme.colors.white,
                    textAlign = TextAlign.Start,
                )
                Divider()
            }

            Text(
                text = content,
                style = WappTheme.typography.contentRegular,
                color = WappTheme.colors.white,
                textAlign = TextAlign.Start,
            )

            SurveyInformationContent(
                title = stringResource(R.string.event),
                content = eventName,
            )

            SurveyInformationContent(
                title = stringResource(R.string.name),
                content = userName,
            )
        }
    }
}

@Composable
private fun ObjectiveQuestionCard(surveyAnswer: SurveyAnswer) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = WappTheme.colors.black25,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = surveyAnswer.questionTitle,
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
                textAlign = TextAlign.Start,
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Rating.values().forEach { rating ->
                    if (rating.toString() == surveyAnswer.questionAnswer) {
                        ObjectiveAnswerIndicator(rating = rating, selected = true)
                    } else {
                        ObjectiveAnswerIndicator(rating = rating, selected = false)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}

@Composable
private fun SubjectiveQuestionCard(surveyAnswer: SurveyAnswer) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = WappTheme.colors.black25,
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = surveyAnswer.questionTitle,
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = surveyAnswer.questionAnswer,
                style = WappTheme.typography.contentRegular,
                color = WappTheme.colors.white,
                textAlign = TextAlign.Start,
            )
            Divider()
        }
    }
}

@Composable
private fun SurveyInformationContent(
    title: String,
    content: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = WappTheme.typography.contentBold,
            color = WappTheme.colors.white,
            textAlign = TextAlign.Start,
            modifier = Modifier.weight(1f),
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = WappTheme.colors.black42,
            ),
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = content,
                style = WappTheme.typography.contentBold,
                color = WappTheme.colors.white,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ObjectiveAnswerIndicator(
    rating: Rating,
    selected: Boolean,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = rating.toDescription().title,
            style = WappTheme.typography.captionRegular,
            color = WappTheme.colors.white,
        )

        FilterChip(
            selected = selected,
            onClick = { },
            label = { },
            colors = FilterChipDefaults.filterChipColors(
                containerColor = WappTheme.colors.black42,
                selectedContainerColor = WappTheme.colors.yellow34,
            ),
            modifier = Modifier.size(height = 40.dp, width = 80.dp),
        )
    }
}
