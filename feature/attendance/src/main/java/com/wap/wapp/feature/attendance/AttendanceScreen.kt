package com.wap.wapp.feature.attendance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.WappRightMainTopBar

@Composable
internal fun AttendanceRoute(
    userId: String,
    viewModel: AttendanceViewModel = hiltViewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }

    AttendanceScreen(snackBarHostState = snackBarHostState)
}

@Composable
internal fun AttendanceScreen(snackBarHostState: SnackbarHostState) {
    Scaffold(
        containerColor = WappTheme.colors.backgroundBlack,
        snackbarHost = { SnackbarHost(snackBarHostState) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            WappRightMainTopBar(
                titleRes = R.string.attendance,
                contentRes = R.string.attendance_content,
            )
        }
    }
}
