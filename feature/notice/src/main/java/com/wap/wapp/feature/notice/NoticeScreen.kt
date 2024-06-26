package com.wap.wapp.feature.notice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.commmon.extensions.TrackScreenViewEvent
import com.wap.wapp.feature.notice.NoticeViewModel.EventsState
import java.time.LocalDate

@Composable
internal fun NoticeRoute(
    viewModel: NoticeViewModel = hiltViewModel(),
) {
    val MonthEvents by viewModel.monthEvents.collectAsStateWithLifecycle()
    val selectedDateEvents by viewModel.selectedDateEvents.collectAsStateWithLifecycle()
    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val onDateSelected = viewModel::updateSelectedDate
    val onCalendarMonthChanged = viewModel::getMonthEvents

    NoticeScreen(
        monthEvents = MonthEvents,
        selectedDateEvents = selectedDateEvents,
        selectedDate = selectedDate,
        onDateSelected = onDateSelected,
        onCalendarMonthChanged = onCalendarMonthChanged,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NoticeScreen(
    monthEvents: EventsState,
    selectedDateEvents: EventsState,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onCalendarMonthChanged: () -> Unit,
) {
    var defaultHeight: Dp by remember { mutableStateOf(0.dp) }
    var expandableHeight: Dp by remember { mutableStateOf(0.dp) }
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = false,
            initialValue = SheetValue.PartiallyExpanded,
            skipHiddenState = true,
        ),
    )

    TrackScreenViewEvent(screenName = "NoticeScreen")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WappTheme.colors.black20),
    ) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContainerColor = WappTheme.colors.black25,
            sheetPeekHeight = defaultHeight,
            sheetShadowElevation = 20.dp,
            sheetContent = {
                BottomSheetContent(
                    expandableHeight = expandableHeight,
                    bottomSheetState = scaffoldState.bottomSheetState,
                    events = selectedDateEvents,
                    selectedDate = selectedDate,
                )
            },
        ) {
            Calendar(
                coroutineScope = coroutineScope,
                bottomSheetState = scaffoldState.bottomSheetState,
                selectedDate = selectedDate,
                monthEventsState = monthEvents,
                onDateSelected = onDateSelected,
                onCalendarMonthChanged = onCalendarMonthChanged,
                measureDefaultModifier = Modifier
                    .fillMaxWidth()
                    .background(WappTheme.colors.black20)
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)

                        defaultHeight = (constraints.maxHeight - placeable.height).toDp()
                        layout(placeable.width, placeable.height) {
                            placeable.placeRelative(0, 0)
                        }
                    },
                measureExpandableModifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        expandableHeight =
                            constraints.maxHeight.toDp() - (placeable.height.toDp() * 2)
                        layout(placeable.width, placeable.height) {
                            placeable.placeRelative(0, 0)
                        }
                    }
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
            )
        }
    }
}
