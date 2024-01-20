package com.wap.wapp.feature.attendance

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.CircleLoader
import com.wap.designsystem.component.WappRightMainTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.model.user.UserRole
import com.wap.wapp.feature.attendance.AttendanceViewModel.AttendanceEvent.Failure
import com.wap.wapp.feature.attendance.AttendanceViewModel.AttendanceEvent.Success
import com.wap.wapp.feature.attendance.AttendanceViewModel.EventAttendanceStatusState
import com.wap.wapp.feature.attendance.AttendanceViewModel.UserRoleState
import com.wap.wapp.feature.attendance.component.AttendanceCheckButton
import com.wap.wapp.feature.attendance.component.AttendanceDialog
import com.wap.wapp.feature.attendance.management.component.NothingToShow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
internal fun AttendanceRoute(
    userId: String,
    viewModel: AttendanceViewModel = hiltViewModel(),
    navigateToProfile: () -> Unit,
    navigateToAttendanceManagement: (String) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val userRoleState by viewModel.userRole.collectAsStateWithLifecycle()
    val eventsAttendanceStatusState
        by viewModel.todayEventsAttendanceStatus.collectAsStateWithLifecycle()
    val attendanceCode by viewModel.attendanceCode.collectAsStateWithLifecycle()
    val selectedEventTitle by viewModel.selectedEventTitle.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.apply {
            getUserRole()
            getTodayEventsAttendanceStatus(userId)

            launch {
                errorFlow.collectLatest { throwable ->
                    snackBarHostState.showSnackbar(message = throwable.toSupportingText())
                }
            }

            launch {
                attendanceEvent.collect { event ->
                    when (event) {
                        is Success -> {
                            snackBarHostState.showSnackbar(
                                message = getString(context, R.string.attendance_success),
                            )
                            getTodayEventsAttendanceStatus(userId)
                        }

                        is Failure -> snackBarHostState.showSnackbar(
                            message = getString(context, R.string.attendance_failure),
                        )
                    }
                }
            }
        }
    }

    AttendanceScreen(
        snackBarHostState = snackBarHostState,
        userRoleState = userRoleState,
        eventsAttendanceStatusState = eventsAttendanceStatusState,
        attendanceCode = attendanceCode,
        selectedEventTitle = selectedEventTitle,
        onAttendanceCodeChanged = viewModel::setAttendanceCode,
        onSelectEventId = viewModel::setSelectedEventId,
        onSelectEventTitle = viewModel::setSelectedEventTitle,
        verifyAttendanceCode = { viewModel.verifyAttendanceCode(userId) },
        navigateToProfile = navigateToProfile,
        navigateToAttendanceManagement = { navigateToAttendanceManagement(userId) },
    )
}

@Composable
internal fun AttendanceScreen(
    snackBarHostState: SnackbarHostState,
    userRoleState: UserRoleState,
    eventsAttendanceStatusState: EventAttendanceStatusState,
    attendanceCode: String,
    selectedEventTitle: String,
    onAttendanceCodeChanged: (String) -> Unit,
    onSelectEventId: (String) -> Unit,
    onSelectEventTitle: (String) -> Unit,
    verifyAttendanceCode: () -> Unit,
    navigateToProfile: () -> Unit,
    navigateToAttendanceManagement: () -> Unit,
) {
    var showAttendanceDialog by remember { mutableStateOf(false) }

    if (showAttendanceDialog) {
        AttendanceDialog(
            attendanceCode = attendanceCode,
            eventTitle = selectedEventTitle,
            onAttendanceCodeChanged = onAttendanceCodeChanged,
            onDismissRequest = { showAttendanceDialog = false },
            onConfirmRequest = verifyAttendanceCode,
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
                    titleRes = R.string.attendance,
                    contentRes = R.string.attendance_content,
                    showBackButton = true,
                    onClickBackButton = navigateToProfile,
                )
                when (userRoleState) {
                    is UserRoleState.Loading -> CircleLoader(modifier = Modifier.fillMaxSize())
                    is UserRoleState.Success -> {
                        when (eventsAttendanceStatusState) {
                            is EventAttendanceStatusState.Loading -> CircleLoader(
                                modifier = Modifier.fillMaxSize(),
                            )

                            is EventAttendanceStatusState.Success -> {
                                if (eventsAttendanceStatusState.events.isEmpty()) {
                                    NothingToShow(title = R.string.no_events_to_attendance)
                                } else {
                                    AttendanceContent(
                                        eventsAttendanceStatus = eventsAttendanceStatusState.events,
                                        onSelectEventId = onSelectEventId,
                                        onSelectEventTitle = onSelectEventTitle,
                                        setAttendanceDialog = { showAttendanceDialog = true },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 15.dp)
                                            .weight(1f),
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (userRoleState == UserRoleState.Success(UserRole.MANAGER)) {
                AttendanceCheckButton(
                    onAttendanceCheckButtonClicked = navigateToAttendanceManagement,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 16.dp),
                )
            }
        }
    }
}
