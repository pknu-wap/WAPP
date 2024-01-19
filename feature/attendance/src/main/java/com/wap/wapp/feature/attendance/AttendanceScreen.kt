package com.wap.wapp.feature.attendance

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappRightMainTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun AttendanceRoute(
    userId: String,
    viewModel: AttendanceViewModel = hiltViewModel(),
    navigateToProfile: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable ->
            snackBarHostState.showSnackbar(
                message = throwable.toSupportingText(),
            )
        }
    }

    AttendanceScreen(snackBarHostState = snackBarHostState, navigateToProfile = navigateToProfile)
}

@Composable
internal fun AttendanceScreen(
    snackBarHostState: SnackbarHostState,
    navigateToProfile: () -> Unit,
) {
    Scaffold(
        containerColor = WappTheme.colors.backgroundBlack,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        contentWindowInsets = WindowInsets(0.dp),
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(modifier = Modifier.fillMaxSize()) {
                WappRightMainTopBar(
                    titleRes = R.string.attendance,
                    contentRes = R.string.attendance_content,
                    showBackButton = true,
                    onClickBackButton = navigateToProfile,
                )
            }
        }
    }
}
