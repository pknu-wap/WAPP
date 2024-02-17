package com.wap.wapp.feature.survey

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.CircleLoader
import com.wap.designsystem.component.WappLeftMainTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.model.user.UserRole
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun SurveyScreen(
    viewModel: SurveyViewModel,
    navigateToSignIn: () -> Unit,
    navigateToSurveyAnswer: (String) -> Unit,
    navigateToSurveyCheck: () -> Unit,
) {
    val context = LocalContext.current
    val surveyFormListUiState = viewModel.surveyFormListUiState.collectAsStateWithLifecycle().value
    val snackBarHostState = remember { SnackbarHostState() }
    var isGuest by rememberSaveable { mutableStateOf(false) }
    var isManager by rememberSaveable { mutableStateOf(false) }

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
                    navigateToSurveyAnswer(it.surveyFormId)
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
                            isGuest = true
                        }

                        // 비회원이 아닌 경우, 목록 호출
                        UserRole.MEMBER -> viewModel.getSurveyFormList()

                        UserRole.MANAGER -> {
                            viewModel.getSurveyFormList()
                            isManager = true
                        }
                    }
                }
            }
        }
    }

    if (isGuest) {
        SurveyGuestScreen(onButtonClicked = navigateToSignIn)
        return
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = WappTheme.colors.backgroundBlack,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            WappLeftMainTopBar(
                titleRes = R.string.survey,
                contentRes = R.string.survey_content,
            )
        },
        contentWindowInsets = WindowInsets(0.dp),
    ) { paddingValues ->
        when (surveyFormListUiState) {
            is SurveyViewModel.SurveyFormListUiState.Init -> {}

            is SurveyViewModel.SurveyFormListUiState.Loading ->
                CircleLoader(modifier = Modifier.fillMaxSize())

            is SurveyViewModel.SurveyFormListUiState.Success -> {
                SurveyContent(
                    surveyFormList = surveyFormListUiState.surveyFormList,
                    isManager = isManager,
                    paddingValues = paddingValues,
                    selectedSurveyForm = viewModel::isSubmittedSurvey,
                    onSurveyCheckButtonClicked = navigateToSurveyCheck,
                )
            }
        }
    }
}
