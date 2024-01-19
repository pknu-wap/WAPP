package com.wap.wapp.feature.attendance.management

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.CircleLoader
import com.wap.designsystem.component.WappRightMainTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.feature.attendance.R
import com.wap.wapp.feature.attendance.management.AttendanceManagementViewModel.EventsState
import com.wap.wapp.feature.attendance.management.component.AttendanceItemCard
import com.wap.wapp.feature.attendance.management.component.AttendanceManagementDialog
import com.wap.wapp.feature.attendance.management.component.NothingToShow
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun AttendanceManagementRoute(
    userId: String,
    viewModel: AttendanceManagementViewModel = hiltViewModel(),
    navigateToManagement: (String) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val eventsState by viewModel.todayEvents.collectAsStateWithLifecycle()
    val attendanceCode by viewModel.attendanceCode.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable ->
            snackBarHostState.showSnackbar(
                message = throwable.toSupportingText(),
            )
        }
    }

    AttendanceManagementScreen(
        snackBarHostState = snackBarHostState,
        eventsState = eventsState,
        attendanceCode = attendanceCode,
        navigateToManagement = { navigateToManagement(userId) },
        onAttendanceCodeChanged = viewModel::setAttendanceCode,
        onSelectEvent = viewModel::setSelectedEventId,
    )
}

@Composable
internal fun AttendanceManagementScreen(
    snackBarHostState: SnackbarHostState,
    eventsState: EventsState,
    attendanceCode: String,
    navigateToManagement: () -> Unit,
    onAttendanceCodeChanged: (String) -> Unit,
    onSelectEvent: (String) -> Unit,
) {
    var showAttendanceManagementDialog by remember { mutableStateOf(false) }

    if (showAttendanceManagementDialog) {
        AttendanceManagementDialog(
            attendanceCode = attendanceCode,
            attendanceStart = {},
            onDismissRequest = { showAttendanceManagementDialog = false },
            onAttendanceCodeChanged = onAttendanceCodeChanged,
        )
    }

    Scaffold(
        containerColor = WappTheme.colors.backgroundBlack,
        snackbarHost = { SnackbarHost(snackBarHostState) },
        contentWindowInsets = WindowInsets(0.dp),
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(modifier = Modifier.fillMaxSize()) {
                WappRightMainTopBar(
                    titleRes = R.string.management_attendance,
                    contentRes = R.string.management_attendance_content,
                    showBackButton = true,
                    onClickBackButton = navigateToManagement,
                )
                when (eventsState) {
                    is EventsState.Loading -> CircleLoader(
                        modifier = Modifier.fillMaxSize(),
                    )

                    is EventsState.Success -> {
                        if (eventsState.events.isEmpty()) {
                            NothingToShow(title = R.string.no_events_to_manage_attendance)
                            return@Column
                        }

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                                .weight(1f),
                        ) {
                            items(
                                items = eventsState.events,
                                key = { event -> event.eventId },
                            ) { event ->
                                AttendanceItemCard(
                                    event = event,
                                    onSelectItemCard = {
                                        onAttendanceCodeChanged("")
                                        onSelectEvent(event.eventId)
                                        showAttendanceManagementDialog = true
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
