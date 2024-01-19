package com.wap.wapp.feature.attendance.management

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.CircleLoader
import com.wap.designsystem.component.WappRightMainTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.feature.attendance.R
import com.wap.wapp.feature.attendance.management.AttendanceManagementViewModel.EventsState
import com.wap.wapp.feature.attendance.management.component.AttendanceItemCard
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
        navigateToManagement = { navigateToManagement(userId) },
    )
}

@Composable
internal fun AttendanceManagementScreen(
    snackBarHostState: SnackbarHostState,
    eventsState: EventsState,
    navigateToManagement: () -> Unit,
) {
    var showAttendanceManagementDialog by remember { mutableStateOf(false) }

    if (showAttendanceManagementDialog) {
        AttendanceManagementDialog(
            attendanceStart = { },
            onDismissRequest = { showAttendanceManagementDialog = false },
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
                                    onSelectItemCard = { showAttendanceManagementDialog = true },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AttendanceManagementDialog(
    attendanceStart: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(WappTheme.colors.black25),
        ) {
            Text(
                text = "출석 코드 설정",
                style = WappTheme.typography.contentBold.copy(fontSize = 20.sp),
                color = WappTheme.colors.yellow34,
                modifier = Modifier.padding(top = 16.dp),
            )

            Divider(
                color = WappTheme.colors.gray82,
                modifier = Modifier.padding(horizontal = 12.dp),
            )

            Text(
                text = generateDialogContentString(),
                style = WappTheme.typography.contentRegular,
                color = WappTheme.colors.white,
            )

            Text(
                text = generateDialogContentString2(),
                style = WappTheme.typography.contentRegular,
                color = WappTheme.colors.white,
                modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 32.dp),
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
            ) {
                Button(
                    onClick = {
                        attendanceStart()
                        onDismissRequest()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WappTheme.colors.yellow34,
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = stringResource(id = R.string.complete),
                        style = WappTheme.typography.titleRegular,
                        color = WappTheme.colors.black,
                    )
                }

                Button(
                    onClick = onDismissRequest,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WappTheme.colors.black25,
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = WappTheme.colors.yellow34,
                            shape = RoundedCornerShape(8.dp),
                        ),
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = WappTheme.typography.titleRegular,
                        color = WappTheme.colors.yellow34,
                    )
                }
            }
        }
    }
}

@Composable
private fun generateDialogContentString() = buildAnnotatedString {
    withStyle(
        style = SpanStyle(
            textDecoration = TextDecoration.Underline,
            color = WappTheme.colors.yellow34,
        ),
    ) {
        append("프로젝트 최종발표")
    }
    append(" 일정에 출석 코드를 설정합니다.")
}

@Composable
private fun generateDialogContentString2() = buildAnnotatedString {
    withStyle(
        style = SpanStyle(
            textDecoration = TextDecoration.Underline,
            color = WappTheme.colors.yellow34,
        ),
    ) {
        append("완료")
    }
    append("를 누르신 후, ")
    withStyle(
        style = SpanStyle(
            textDecoration = TextDecoration.Underline,
            color = WappTheme.colors.yellow34,
        ),
    ) {
        append("10분간")
    }
    append(" 출석이 진행됩니다.")
}
