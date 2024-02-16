package com.wap.wapp.feature.survey.check

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.CircleLoader
import com.wap.designsystem.component.WappRightMainTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SurveyCheckScreen(
    viewModel: SurveyCheckViewModel = hiltViewModel(),
    navigateToSurveyDetail: (String) -> Unit,
    navigateToSurvey: () -> Unit,
) {
    val surveyListUiState = viewModel.surveyListUiState.collectAsStateWithLifecycle().value
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest {
            snackBarHostState.showSnackbar(it.toSupportingText())
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = WappTheme.colors.backgroundBlack,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            WappRightMainTopBar(
                titleRes = R.string.survey_check,
                contentRes = R.string.survey_check_content,
                showBackButton = true,
                onClickBackButton = navigateToSurvey,
            )
        },
        contentWindowInsets = WindowInsets(0.dp),
    ) { paddingValues ->
        when (surveyListUiState) {
            is SurveyCheckViewModel.SurveyListUiState.Init -> {
                CircleLoader(modifier = Modifier.fillMaxSize())
            }

            is SurveyCheckViewModel.SurveyListUiState.Success -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 40.dp)
                        .padding(paddingValues),
                ) {
                    val surveyList = surveyListUiState.surveyList
                    items(surveyList) { survey ->
                        SurveyItemCard(
                            onCardClicked = navigateToSurveyDetail,
                            survey = survey,
                        )
                    }
                }
            }
        }
    }
}
