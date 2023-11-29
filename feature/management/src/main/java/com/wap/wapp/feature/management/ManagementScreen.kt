package com.wap.wapp.feature.management

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.feature.management.ManagementViewModel.ManagerState
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ManagementScreen(
    showManageCodeDialog: () -> Unit,
    viewModel: ManagementViewModel = hiltViewModel(),
    navigateToEventRegistration: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val surveyList = viewModel.surveyList.collectAsState().value
    val eventList = viewModel.eventList.collectAsState().value

    LaunchedEffect(true) {
        viewModel.managerState.collectLatest { managerState ->
            when (managerState) {
                ManagerState.NonManager -> {
                    showManageCodeDialog()
                }

                ManagerState.Init -> {}
                ManagerState.Manager -> {
                    viewModel.apply {
                        getSurveyList()
                        getMonthEventList()
                    }
                }
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
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.management),
                        style = WappTheme.typography.titleBold,
                        color = WappTheme.colors.white,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WappTheme.colors.black25,
                ),
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .padding(vertical = 16.dp, horizontal = 8.dp),
        ) {
            ManagementEventContent(
                eventList = eventList,
                onCardClicked = {},
                onAddEventButtonClicked = navigateToEventRegistration,
            )

            ManagementSurveyContent(
                surveyList = surveyList,
                onCardClicked = { },
                onAddSurveyButtonClicked = {},
                modifier = Modifier.padding(top = 20.dp),
            )
        }
    }
}

@Composable
internal fun ManagementCardColor(currentIndex: Int): Color {
    return if (currentIndex % 2 == 0) {
        WappTheme.colors.black82
    } else {
        WappTheme.colors.black42
    }
}

@Preview
@Composable
fun previewManagementScreen() {
    WappTheme {
        // ManageScreen()
    }
}
