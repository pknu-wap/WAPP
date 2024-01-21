package com.wap.wapp.feature.attendance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.CircleLoader
import com.wap.designsystem.component.NothingToShow
import com.wap.designsystem.component.WappButton
import com.wap.designsystem.component.WappLeftMainTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.model.user.UserRole
import com.wap.wapp.feature.attendance.AttendanceViewModel.AttendanceEvent.Failure
import com.wap.wapp.feature.attendance.AttendanceViewModel.AttendanceEvent.Success
import com.wap.wapp.feature.attendance.AttendanceViewModel.EventAttendanceStatusState
import com.wap.wapp.feature.attendance.AttendanceViewModel.UserRoleState
import com.wap.wapp.feature.attendance.component.AttendanceCheckButton
import com.wap.wapp.feature.attendance.component.AttendanceDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
internal fun AttendanceRoute(
    viewModel: AttendanceViewModel = hiltViewModel(),
    navigateToSignIn: () -> Unit,
    navigateToAttendanceManagement: () -> Unit,
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
                                getString(context, R.string.attendance_success),
                            )
                            getTodayEventsAttendanceStatus()
                        }

                        is Failure -> snackBarHostState.showSnackbar(
                            getString(context, R.string.attendance_failure),
                        )
                    }
                }
            }
        }
    }

    when (userRoleState) {
        is UserRoleState.Loading -> CircleLoader(modifier = Modifier.fillMaxSize())
        is UserRoleState.Success -> {
            when ((userRoleState as UserRoleState.Success).userRole) {
                UserRole.GUEST -> AttendanceGuestScreen(onButtonClicked = navigateToSignIn)
                UserRole.MANAGER, UserRole.MEMBER -> AttendanceScreen(
                    userRole = (userRoleState as UserRoleState.Success).userRole,
                    snackBarHostState = snackBarHostState,
                    eventsAttendanceStatusState = eventsAttendanceStatusState,
                    attendanceCode = attendanceCode,
                    selectedEventTitle = selectedEventTitle,
                    clearAttendanceCode = viewModel::clearAttendanceCode,
                    onAttendanceCodeChanged = viewModel::setAttendanceCode,
                    onSelectEventId = viewModel::setSelectedEventId,
                    onSelectEventTitle = viewModel::setSelectedEventTitle,
                    verifyAttendanceCode = viewModel::verifyAttendanceCode,
                    navigateToAttendanceManagement = navigateToAttendanceManagement,
                )
            }
        }
    }
}

@Composable
internal fun AttendanceScreen(
    userRole: UserRole,
    snackBarHostState: SnackbarHostState,
    eventsAttendanceStatusState: EventAttendanceStatusState,
    attendanceCode: String,
    selectedEventTitle: String,
    clearAttendanceCode: () -> Unit,
    onAttendanceCodeChanged: (String) -> Unit,
    onSelectEventId: (String) -> Unit,
    onSelectEventTitle: (String) -> Unit,
    verifyAttendanceCode: () -> Unit,
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            WappLeftMainTopBar(
                titleRes = R.string.attendance,
                contentRes = R.string.attendance_content,
            )
            when (eventsAttendanceStatusState) {
                is EventAttendanceStatusState.Loading ->
                    CircleLoader(Modifier.fillMaxSize())

                is EventAttendanceStatusState.Success -> {
                    Box {
                        if (eventsAttendanceStatusState.events.isEmpty()) {
                            NothingToShow(title = R.string.no_events_to_attendance)
                        } else {
                            AttendanceContent(
                                eventsAttendanceStatus =
                                eventsAttendanceStatusState.events,
                                onSelectEventId = onSelectEventId,
                                onSelectEventTitle = onSelectEventTitle,
                                setAttendanceDialog = {
                                    clearAttendanceCode()
                                    showAttendanceDialog = true
                                },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 15.dp),
                            )
                        }
                        if (userRole == UserRole.MANAGER) {
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
        }
    }
}

@Composable
internal fun AttendanceGuestScreen(
    onButtonClicked: () -> Unit,
) {
    Surface(
        color = WappTheme.colors.backgroundBlack,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.attendance_guset_title),
                style = WappTheme.typography.titleBold,
                color = WappTheme.colors.white,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            Text(
                text = stringResource(R.string.attendance_guest_content),
                style = WappTheme.typography.captionMedium,
                color = WappTheme.colors.white,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            WappButton(
                textRes = R.string.go_to_signin,
                onClick = onButtonClicked,
                modifier = Modifier.padding(horizontal = 32.dp),
            )
        }
    }
}
