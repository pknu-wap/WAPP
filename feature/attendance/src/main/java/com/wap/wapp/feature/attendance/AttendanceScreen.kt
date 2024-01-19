package com.wap.wapp.feature.attendance

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.wap.wapp.feature.attendance.component.AttendanceDialog
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

    LaunchedEffect(true) {
        viewModel.apply {
            getUserRole()
            getTodayEventsAttendanceStatus(userId)

            launch {
                errorFlow.collectLatest { throwable ->
                    snackBarHostState.showSnackbar(
                        message = throwable.toSupportingText(),
                    )
                }
            }

            launch {
                attendanceEvent.collectLatest { event ->
                    when (event) {
                        // 출석 성공 했을 경우
                        is Success -> {}
                        // 출석 실패 했을 경우
                        is Failure -> {}
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
        verifyAttendanceCode = viewModel::verifyAttendanceCode,
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

                            is EventAttendanceStatusState.Success -> LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 15.dp)
                                    .weight(1f),
                            ) {
                                items(
                                    items = eventsAttendanceStatusState.events,
                                    key = { it.eventId },
                                ) { attendanceStatus ->
                                    AttendanceItemCard(
                                        eventTitle = attendanceStatus.title,
                                        eventContent = attendanceStatus.content,
                                        remainAttendanceDateTime =
                                        attendanceStatus.remainAttendanceDateTime,
                                        isAttendance = attendanceStatus.isAttendance,
                                        onSelectItemCard = {
                                            onSelectEventId(attendanceStatus.eventId)
                                            onSelectEventTitle(attendanceStatus.title)
                                            showAttendanceDialog = true
                                        },
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

@Composable
private fun AttendanceItemCard(
    eventTitle: String,
    eventContent: String,
    remainAttendanceDateTime: String,
    isAttendance: Boolean,
    onSelectItemCard: () -> Unit = {},
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = WappTheme.colors.black25),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelectItemCard() },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = eventTitle,
                    color = WappTheme.colors.white,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    style = WappTheme.typography.titleBold,
                )
                if (isAttendance) {
                    Text(
                        text = "출석 완료",
                        color = WappTheme.colors.greenAB,
                        style = WappTheme.typography.captionMedium,
                    )
                    return@Row
                }
                Text(
                    text = remainAttendanceDateTime,
                    color = WappTheme.colors.yellow34,
                    style = WappTheme.typography.captionMedium,
                )
            }
            Text(
                text = eventContent,
                color = WappTheme.colors.grayBD,
                style = WappTheme.typography.contentMedium,
            )
        }
    }
}

@Composable
private fun AttendanceCheckButton(
    onAttendanceCheckButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedButton(
        modifier = modifier.height(48.dp),
        onClick = { onAttendanceCheckButtonClicked() },
        colors = ButtonDefaults.buttonColors(
            contentColor = WappTheme.colors.black,
            containerColor = WappTheme.colors.yellow34,
            disabledContentColor = WappTheme.colors.white,
            disabledContainerColor = WappTheme.colors.grayA2,
        ),
        shape = RoundedCornerShape(8.dp),
        content = {
            Row {
                Text(
                    text = stringResource(R.string.go_to_management_attendance_code),
                    style = WappTheme.typography.contentRegular,
                )
            }
        },
    )
}
