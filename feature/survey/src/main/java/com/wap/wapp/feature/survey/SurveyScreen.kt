package com.wap.wapp.feature.survey

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappTitle
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.commmon.util.DateUtil.yyyyMMddFormatter
import com.wap.wapp.core.model.survey.SurveyForm
import kotlinx.coroutines.flow.collectLatest
import java.time.Duration
import java.time.LocalDateTime

@Composable
internal fun SurveyScreen(
    viewModel: SurveyViewModel,
    navigateToSurveyAnswer: (Int) -> Unit,
) {
    val context = LocalContext.current
    val surveyFormListUiState = viewModel.surveyFormListUiState.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.surveyEvent.collectLatest {
            when (it) {
                is SurveyViewModel.SurveyUiEvent.Failure -> {
                    snackBarHostState.showSnackbar(it.throwable.toSupportingText())
                }

                is SurveyViewModel.SurveyUiEvent.AlreadySubmitted -> {
                    snackBarHostState.showSnackbar(
                        context.getString(R.string.alreay_submitted_description),
                    )
                }

                is SurveyViewModel.SurveyUiEvent.NotSubmitted -> {
                    navigateToSurveyAnswer(it.eventId)
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = WappTheme.colors.backgroundBlack,
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { paddingValues ->
        when (surveyFormListUiState) {
            is SurveyViewModel.SurveyFormListUiState.Init -> { }
            is SurveyViewModel.SurveyFormListUiState.Success -> {
                SurveyContent(
                    surveyFormList = surveyFormListUiState.surveyFormList,
                    paddingValues = paddingValues,
                    selectedSurveyForm = viewModel::isSubmittedSurvey,
                )
            }
        }
    }
}

@Composable
private fun SurveyContent(
    surveyFormList: List<SurveyForm>,
    paddingValues: PaddingValues,
    selectedSurveyForm: (Int) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(paddingValues)
            .padding(vertical = 16.dp, horizontal = 8.dp),
    ) {
        WappTitle(
            title = stringResource(R.string.survey_title),
            content = stringResource(R.string.survey_content),
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(surveyFormList) { surveyForm ->
                SurveyFormItemCard(
                    surveyForm = surveyForm,
                    selectedSurveyForm = selectedSurveyForm,
                )
            }
        }
    }
}

@Composable
private fun SurveyFormItemCard(
    surveyForm: SurveyForm,
    selectedSurveyForm: (Int) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = WappTheme.colors.black25,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { selectedSurveyForm(surveyForm.eventId) },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = surveyForm.title,
                    color = WappTheme.colors.white,
                    style = WappTheme.typography.titleBold,
                )
                Text(
                    text = calculateDeadline(surveyForm.deadline),
                    color = WappTheme.colors.yellow34,
                    style = WappTheme.typography.captionMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                )
            }

            Text(
                text = surveyForm.content,
                color = WappTheme.colors.grayBD,
                style = WappTheme.typography.contentMedium,
            )
        }
    }
}

private fun calculateDeadline(deadline: LocalDateTime): String {
    val currentDateTime = LocalDateTime.now()
    val duration = Duration.between(currentDateTime, deadline)

    if (duration.toMinutes() < 60) {
        val leftMinutes = duration.toMinutes().toString()
        return leftMinutes + "분 후 마감"
    }

    if (duration.toHours() < 24) {
        val leftHours = duration.toHours().toString()
        return leftHours + "시간 후 마감"
    }

    if (duration.toDays() < 31) {
        val leftDays = duration.toDays().toString()
        return leftDays + "일 후 마감"
    }

    return deadline.format(yyyyMMddFormatter) + " 마감"
}
