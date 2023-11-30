package com.wap.wapp.feature.survey

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappTitle
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.model.survey.SurveyForm
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SurveyScreen(
    viewModel: SurveyViewModel = hiltViewModel(),
    selectedSurveyForm: (Int) -> Unit,
) {
    val surveyFormListUiState = viewModel.surveyFormListUiState.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.surveyEvent.collectLatest {
            when (it) {
                is SurveyViewModel.SurveyUiEvent.Failure -> {
                    snackBarHostState.showSnackbar(it.throwable.toSupportingText())
                }
            }
        }
    }

    when (surveyFormListUiState) {
        is SurveyViewModel.SurveyFormListUiState.Init -> { }
        is SurveyViewModel.SurveyFormListUiState.Success -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = WappTheme.colors.black25,
                        ),
                        title = {
                            Text(
                                text = "설문",
                                color = WappTheme.colors.white,
                                style = WappTheme.typography.titleBold,
                            )
                        },
                    )
                },
                modifier = Modifier.fillMaxSize(),
                containerColor = WappTheme.colors.backgroundBlack,
                snackbarHost = { SnackbarHost(snackBarHostState) },
            ) { paddingValues ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(vertical = 16.dp, horizontal = 8.dp),
                ) {
                    WappTitle(
                        title = "설문 작성",
                        content = "참가한 행사에 대해 설문에 응답하세요!",
                    )

                    LazyColumn {
                        items(surveyFormListUiState.surveyFormList) { surveyForm ->
                            SurveyFormItemCard(
                                surveyForm = surveyForm,
                                selectedSurveyForm = selectedSurveyForm,
                            )
                        }
                    }
                }
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
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .clickable { selectedSurveyForm(surveyForm.eventId) },
        ) {
            Text(
                text = surveyForm.title,
                color = WappTheme.colors.white,
                style = WappTheme.typography.contentBold,
            )

            Column {
                Text(
                    text = surveyForm.content,
                    color = WappTheme.colors.white,
                    style = WappTheme.typography.captionMedium,
                )
                Divider(color = WappTheme.colors.yellow)
            }
        }
    }
}

@Preview
@Composable
private fun previewSurveyScreen() {
    WappTheme {
    }
}
