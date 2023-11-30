package com.wap.wapp.feature.survey.answer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.designresource.R
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SurveyAnswerScreen(
    onBackButtonClicked: () -> Unit,
    eventId: Int,
    viewModel: SurveyAnswerViewModel = hiltViewModel(),
) {
    val surveyFormUiState = viewModel.surveyFormUiState.collectAsState().value
    val snackBarHostState = remember { SnackbarHostState() }
    val scrollState = rememberScrollState()

    LaunchedEffect(true) {
        viewModel.getSurveyForm(eventId)

        viewModel.surveyAnswerEvent.collectLatest {
            when (it) {
                is SurveyAnswerViewModel.SurveyAnswerUiEvent.Failure -> {
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
                        text = "설문 응답",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = WappTheme.typography.contentBold,
                    )
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = stringResource(
                            id = com.wap.wapp.core.designsystem.R.string.back_button,
                        ),
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .clickable { onBackButtonClicked() },
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WappTheme.colors.yellow,
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
            when (surveyFormUiState) {
                is SurveyAnswerViewModel.SurveyFormUiState.Init -> {}

                is SurveyAnswerViewModel.SurveyFormUiState.Success -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                    }
                }
            }
        }
    }
}
