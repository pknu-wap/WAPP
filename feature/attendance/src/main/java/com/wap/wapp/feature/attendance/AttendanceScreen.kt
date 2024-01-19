package com.wap.wapp.feature.attendance

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun AttendanceRoute(
    userId: String,
    viewModel: AttendanceViewModel = hiltViewModel(),
) {
    AttendanceScreen()
}

@Composable
internal fun AttendanceScreen() {
}
