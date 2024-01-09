
package com.wap.wapp.feature.management

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappMainTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.feature.management.ManagementViewModel.ManagerState
import com.wap.wapp.feature.management.validation.ManagementCodeValidationDialog
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun ManagementRoute(
    navigateToEventEdit: (String, String) -> Unit,
    navigateToEventRegistration: () -> Unit,
    navigateToSurveyRegistration: () -> Unit,
    navigateToSurveyFormEdit: (String) -> Unit,
    viewModel: ManagementViewModel = hiltViewModel(),
) {
    var isShowDialog by rememberSaveable { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }
    val surveyFormsState by viewModel.surveyFormList.collectAsStateWithLifecycle()
    val eventsState by viewModel.eventList.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable ->
            snackBarHostState.showSnackbar(
                message = throwable.toSupportingText(),
            )
        }
    }

    LaunchedEffect(true) {
        viewModel.managerState.collectLatest { managerState ->
            when (managerState) {
                ManagerState.Init -> {}
                ManagerState.NonManager -> { isShowDialog = true }
                ManagerState.Manager -> viewModel.getEventSurveyList()
            }
        }
    }

    ManagementScreen(
        snackBarHostState = snackBarHostState,
        surveyFormsState = surveyFormsState,
        eventsState = eventsState,
        navigateToEventRegistration = navigateToEventRegistration,
        navigateToSurveyRegistration = navigateToSurveyRegistration,
        navigateToSurveyFormEdit = navigateToSurveyFormEdit,
        navigateToEventEdit = navigateToEventEdit,
    )

    if (isShowDialog) {
        ManagementCodeValidationDialog(
            onDismissRequest = { isShowDialog = false },
            showToast = { throwable ->
                showToast(throwable.toSupportingText(), context)
            },
        )
    }
}

@Composable
internal fun ManagementScreen(
    snackBarHostState: SnackbarHostState,
    surveyFormsState: ManagementViewModel.SurveyFormsState,
    eventsState: ManagementViewModel.EventsState,
    navigateToEventEdit: (String, String) -> Unit,
    navigateToEventRegistration: () -> Unit,
    navigateToSurveyRegistration: () -> Unit,
    navigateToSurveyFormEdit: (String) -> Unit,
) {
    Scaffold(
        containerColor = WappTheme.colors.backgroundBlack,
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { paddingValues ->
        WappMainTopBar(
            titleRes = R.string.management,
            contentRes = R.string.management_content,
        )

        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            ManagementEventCard(
                eventsState = eventsState,
                onCardClicked = navigateToEventEdit,
                onAddEventButtonClicked = navigateToEventRegistration,
            )

            ManagementSurveyCard(
                surveyFormsState = surveyFormsState,
                onCardClicked = navigateToSurveyFormEdit,
                onAddSurveyButtonClicked = navigateToSurveyRegistration,
            )
        }
    }
}

@Composable
internal fun ManagementCardColor(currentIndex: Int): Color =
    if (currentIndex % 2 == 0) {
        WappTheme.colors.black82
    } else {
        WappTheme.colors.black42
    }

private fun showToast(text: String, context: Context) =
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
