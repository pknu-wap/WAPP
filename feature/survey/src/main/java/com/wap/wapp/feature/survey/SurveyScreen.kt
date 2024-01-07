package com.wap.wapp.feature.survey

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappMainTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.model.survey.SurveyForm
import com.wap.wapp.core.model.user.UserRole
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SurveyScreen(
    viewModel: SurveyViewModel,
    navigateToSignIn: () -> Unit,
    navigateToSurveyAnswer: (String) -> Unit,
) {
    val context = LocalContext.current
    val surveyFormListUiState = viewModel.surveyFormListUiState.collectAsStateWithLifecycle().value
    val snackBarHostState = remember { SnackbarHostState() }
    var isShowGuestDialog by rememberSaveable { mutableStateOf(false) }

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

    LaunchedEffect(true) {
        viewModel.userRoleUiState.collectLatest { userRoleUiState ->
            when (userRoleUiState) {
                is SurveyViewModel.UserRoleUiState.Init -> {}
                is SurveyViewModel.UserRoleUiState.Success -> {
                    when (userRoleUiState.userRole) {
                        UserRole.GUEST -> {
                            isShowGuestDialog = true
                        }

                        // 비회원이 아닌 경우, 목록 호출
                        UserRole.MEMBER, UserRole.MANAGER -> {
                            viewModel.getSurveyFormList()
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = WappTheme.colors.backgroundBlack,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            WappMainTopBar(
                titleRes = R.string.survey,
                contentRes = R.string.survey_content,
            )
        },
        contentWindowInsets = WindowInsets(0.dp),
    ) { paddingValues ->
        when (surveyFormListUiState) {
            is SurveyViewModel.SurveyFormListUiState.Init -> {}
            is SurveyViewModel.SurveyFormListUiState.Success -> {
                SurveyContent(
                    surveyFormList = surveyFormListUiState.surveyFormList,
                    paddingValues = paddingValues,
                    selectedSurveyForm = viewModel::isSubmittedSurvey,
                )
            }
        }
    }

    if (isShowGuestDialog) {
        SurveyGuestDialog(
            onDismissRequest = { isShowGuestDialog = false },
            onButtonClicked = navigateToSignIn,
        )
    }
}

@Composable
private fun SurveyContent(
    surveyFormList: List<SurveyForm>,
    paddingValues: PaddingValues,
    selectedSurveyForm: (String) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
    ) {
        items(surveyFormList) { surveyForm ->
            SurveyFormItemCard(
                surveyForm = surveyForm,
                selectedSurveyForm = selectedSurveyForm,
            )
        }
    }
}

@Composable
private fun SurveyFormItemCard(
    surveyForm: SurveyForm,
    selectedSurveyForm: (String) -> Unit,
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
            modifier = Modifier.padding(16.dp),
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
                    text = surveyForm.calculateDeadline(),
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
