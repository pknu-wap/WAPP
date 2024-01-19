package com.wap.wapp.feature.attendance.management

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.CircleLoader
import com.wap.designsystem.component.WappRightMainTopBar
import com.wap.wapp.core.commmon.extensions.toSupportingText
import com.wap.wapp.core.designresource.R.drawable.ic_forward_yellow
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.feature.attendance.R
import com.wap.wapp.feature.attendance.management.AttendanceManagementViewModel.EventsState
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

                    is EventsState.Success -> LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                            .weight(1f),
                    ) {
                        items(
                            items = eventsState.events,
                            key = { event -> event.eventId },
                        ) { event -> AttendanceItemCard(event = event) }
                    }
                }
            }
        }
    }
}

@Composable
private fun AttendanceItemCard(
    event: Event,
    onSelectItemCard: () -> Unit = {},
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = WappTheme.colors.black25),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelectItemCard() },
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = event.title,
                        color = WappTheme.colors.white,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        style = WappTheme.typography.titleBold,
                    )
                    Text(
                        text = "10분 후 마감",
                        color = WappTheme.colors.yellow34,
                        style = WappTheme.typography.captionMedium,
                    )
                }
                Text(
                    text = event.content,
                    color = WappTheme.colors.grayBD,
                    style = WappTheme.typography.contentMedium,
                )
            }
            Image(
                painter = painterResource(id = ic_forward_yellow),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp),
            )
        }
    }
}
