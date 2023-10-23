package com.wap.wapp.feature.manage

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.feature.manage.ManageViewModel.ManagerState
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ManageScreen(
    showManageCodeDialog: () -> Unit,
    viewModel: ManageViewModel = hiltViewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.managerState.collectLatest { managerState ->
            when (managerState) {
                ManagerState.NonManager -> {
                    showManageCodeDialog()
                }
                ManagerState.Init -> { }
                ManagerState.Manager -> { }
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
                        text = stringResource(id = R.string.manage),
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
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
        ) {
            Text("avoid to ktlint")
        }
    }
}

@Preview
@Composable
fun previewManageScreen() {
    WappTheme {
        // ManageScreen()
    }
}
