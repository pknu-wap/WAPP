package com.wap.wapp.feature.survey

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappTitle
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.model.survey.SurveyForm
import kotlinx.coroutines.flow.collectLatest

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
            SurveyContent(
                surveyFormList = surveyFormListUiState.surveyFormList,
                snackBarHostState = snackBarHostState,
                selectedSurveyForm = selectedSurveyForm,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SurveyContent(
    surveyFormList: List<SurveyForm>,
    snackBarHostState: SnackbarHostState,
    selectedSurveyForm: (Int) -> Unit,
) {
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
}

@Composable
private fun SurveyFormItemCard(
    surveyForm: SurveyForm,
    selectedSurveyForm: (Int) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = WappTheme.colors.black,
        ),
        border = BorderStroke(1.dp, WappTheme.colors.white),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { selectedSurveyForm(surveyForm.eventId) },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(16.dp),
        ) {
            Text(
                text = surveyForm.title,
                color = WappTheme.colors.white,
                style = WappTheme.typography.titleBold,
            )

            Text(
                text = surveyForm.content,
                color = WappTheme.colors.yellow34,
                style = WappTheme.typography.contentRegular,
            )
        }
    }
}
