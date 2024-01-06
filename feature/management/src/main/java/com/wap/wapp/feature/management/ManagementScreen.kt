package com.wap.wapp.feature.management

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappMainTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.feature.management.ManagementViewModel.ManagerState
import com.wap.wapp.feature.management.dialog.ManagementCodeValidationDialog
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun ManagementRoute(
    navigateToEventEdit: (String, String) -> Unit,
    navigateToEventRegistration: () -> Unit,
    navigateToSurveyRegistration: () -> Unit,
    navigateToSurveyCheck: (String) -> Unit,
    viewModel: ManagementViewModel = hiltViewModel(),
) {
    var isShowDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    ManagementScreen(
        viewModel = viewModel,
        showManageCodeDialog = { isShowDialog = true },
        navigateToEventRegistration = navigateToEventRegistration,
        navigateToSurveyRegistration = navigateToSurveyRegistration,
        navigateToSurveyCheck = navigateToSurveyCheck,
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
    viewModel: ManagementViewModel,
    showManageCodeDialog: () -> Unit,
    navigateToEventEdit: (String, String) -> Unit,
    navigateToEventRegistration: () -> Unit,
    navigateToSurveyRegistration: () -> Unit,
    navigateToSurveyCheck: (String) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val surveysState by viewModel.surveyList.collectAsState()
    val eventsState by viewModel.eventList.collectAsState()

    LaunchedEffect(true) {
        viewModel.managerState.collectLatest { managerState ->
            when (managerState) {
                ManagerState.Init -> {}
                ManagerState.NonManager -> showManageCodeDialog()
                ManagerState.Manager -> viewModel.getEventSurveyList()
            }
        }

        viewModel.errorFlow.collectLatest { throwable ->
            snackBarHostState.showSnackbar(
                message = throwable.toSupportingText(),
            )
        }
    }
    Scaffold(
        containerColor = WappTheme.colors.backgroundBlack,
        contentWindowInsets = WindowInsets(0.dp),
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            WappMainTopBar(
                titleRes = R.string.management,
                contentRes = R.string.management_content,
            )

            ManagementEventContent(
                eventsState = eventsState,
                onCardClicked = navigateToEventEdit,
                onAddEventButtonClicked = navigateToEventRegistration,
                modifier = Modifier.padding(horizontal = 8.dp),
            )

            ManagementSurveyContent(
                surveysState = surveysState,
                onCardClicked = navigateToSurveyCheck,
                onAddSurveyButtonClicked = navigateToSurveyRegistration,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 8.dp),
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
