package com.wap.wapp.feature.attendance

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.wap.wapp.feature.attendance.AttendanceViewModel.EventsState
import com.wap.wapp.feature.attendance.AttendanceViewModel.UserRoleState
import kotlinx.coroutines.flow.collectLatest

@Composable
internal fun AttendanceRoute(
    userId: String,
    viewModel: AttendanceViewModel = hiltViewModel(),
    navigateToProfile: () -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val userRoleState by viewModel.userRole.collectAsStateWithLifecycle()
    val eventsState by viewModel.todayEvents.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.errorFlow.collectLatest { throwable ->
            snackBarHostState.showSnackbar(
                message = throwable.toSupportingText(),
            )
        }
    }

    AttendanceScreen(
        snackBarHostState = snackBarHostState,
        userRoleState = userRoleState,
        eventsState = eventsState,
        navigateToProfile = navigateToProfile,
    )
}

@Composable
internal fun AttendanceScreen(
    snackBarHostState: SnackbarHostState,
    userRoleState: UserRoleState,
    eventsState: EventsState,
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
                when (userRoleState) {
                    is UserRoleState.Loading -> CircleLoader(modifier = Modifier.fillMaxSize())
                    is UserRoleState.Success -> LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    ) {}
                }
            }

            if (userRoleState == UserRoleState.Success(UserRole.MANAGER)) {
                AttendanceCheckButton(
                    onAttendanceCheckButtonClicked = {},
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 16.dp),
                )
            }
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
