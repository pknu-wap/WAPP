package com.wap.wapp.feature.notice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.feature.notice.DateUtil.Companion.MONTH_DATE_START_INDEX
import com.wap.wapp.feature.notice.NoticeViewModel.EventsState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NoticeScreen(
    events: EventsState,
    dateAndDayOfWeek: Pair<String, String>,
    dateUtil: DateUtil,
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WappTheme.colors.black25),
    ) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContainerColor = WappTheme.colors.black25,
            sheetPeekHeight = defaultHeight,
            sheetContent = {
                BottomSheetContent(
                    expandableHeight,
                    events,
                    dateAndDayOfWeek,
                )
            },
        ) {
            Calendar(
                coroutineScope = coroutineScope,
                bottomSheetState = scaffoldState.bottomSheetState,
                dateAndDayOfWeek = dateAndDayOfWeek,
                measureDefaultModifier = Modifier
                    .fillMaxWidth()
                    .background(WappTheme.colors.black25)
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
                currentDate = dateUtil.generateNowDate(),
                eventDates = listOf(
                    LocalDate.of(2023, 11, 22),
                    LocalDate.of(2023, 11, 19),
                    LocalDate.of(2023, 11, 18),
                    LocalDate.of(2023, 11, 15),
                ),
                selectedDate = dateUtil.generateNowDate(),
            )
        }
    }
}

@Composable
private fun BottomSheetContent(
    expandableHeight: Dp,
    events: EventsState,
    dateAndDayOfWeek: Pair<String, String>,
) {
    val date = dateAndDayOfWeek.first
    val dayOfWeek = dateAndDayOfWeek.second

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.height(expandableHeight),
    ) {
        Text(
            text = "${date.substring(MONTH_DATE_START_INDEX)} $dayOfWeek",
            style = WappTheme.typography.titleBold,
            color = WappTheme.colors.white,
            modifier = Modifier.padding(start = 15.dp, bottom = 15.dp),
        )
        HandleEventsState(events = events)
    }
}

@Composable
private fun HandleEventsState(events: EventsState) =
    when (events) {
        is EventsState.Loading -> Loader()
        is EventsState.Success -> EventsList(events.events)
        is EventsState.Failure -> Unit
    }

@Composable
private fun EventsList(events: List<Event>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 15.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        itemsIndexed(
            items = events,
            key = { _, event -> event.id },
        ) { _, event ->
            EventItem(event = event)
        }
    }
}

@Composable
private fun EventItem(event: Event) {
    val formatter = DateTimeFormatter.ofPattern("MM-dd")
    Column {
        Row(
            modifier = Modifier
                .background(WappTheme.colors.black25)
                .fillMaxWidth()
                .padding(top = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = formatter.format(event.period),
                style = WappTheme.typography.contentBold,
                color = WappTheme.colors.white,
            )
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size(width = 4.dp, height = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(WappTheme.colors.yellow),
            )
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(start = 12.dp),
            ) {
                Text(
                    text = event.id,
                    style = WappTheme.typography.contentRegular,
                    color = WappTheme.colors.white,
                )
                Text(
                    text = formatter.format(event.period),
                    style = WappTheme.typography.captionRegular,
                    color = WappTheme.colors.grayBD,
                )
            }
        }
        Divider(
            color = WappTheme.colors.gray82,
            thickness = (0.5).dp,
            modifier = Modifier.padding(top = 15.dp),
        )
    }
}
