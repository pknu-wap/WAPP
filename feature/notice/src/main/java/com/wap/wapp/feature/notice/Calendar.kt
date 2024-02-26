package com.wap.wapp.feature.notice

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.CircleLoader
import com.wap.wapp.core.commmon.util.DateUtil.DAYS_IN_WEEK
import com.wap.wapp.core.commmon.util.DateUtil.DaysOfWeek
import com.wap.wapp.core.commmon.util.DateUtil.YEAR_MONTH_END_INDEX
import com.wap.wapp.core.commmon.util.DateUtil.YEAR_MONTH_START_INDEX
import com.wap.wapp.core.commmon.util.DateUtil.generateNowDate
import com.wap.wapp.core.commmon.util.DateUtil.yyyyMMddFormatter
import com.wap.wapp.core.designresource.R.drawable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Calendar(
    coroutineScope: CoroutineScope,
    bottomSheetState: SheetState,
    selectedDate: LocalDate,
    monthEventsState: NoticeViewModel.EventsState,
    measureDefaultModifier: Modifier,
    measureExpandableModifier: Modifier,
    onDateSelected: (LocalDate) -> Unit,
    onCalendarMonthChanged: () -> Unit,
) {
    Column(
        modifier = measureDefaultModifier,
    ) {
        CalendarHeader(
            coroutineScope = coroutineScope,
            bottomSheetState = bottomSheetState,
            selectedDate = selectedDate,
            onDateSelected = onDateSelected,
            onCalendarMonthChanged = onCalendarMonthChanged,
            modifier = measureExpandableModifier,
        )

        when (monthEventsState) {
            is NoticeViewModel.EventsState.Loading ->
                CircleLoader(modifier = Modifier.fillMaxSize())

            is NoticeViewModel.EventsState.Success -> {
                val eventDates = monthEventsState.events.map {
                    it.startDateTime.toLocalDate()
                }
                CalendarBody(
                    selectedDate = selectedDate,
                    eventsDate = eventDates,
                    onDateSelected = onDateSelected,
                )
            }

            is NoticeViewModel.EventsState.Failure -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalendarHeader(
    coroutineScope: CoroutineScope,
    bottomSheetState: SheetState,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onCalendarMonthChanged: () -> Unit,
    modifier: Modifier,
) = Box(
    modifier = modifier,
) {
    val date = selectedDate.format(yyyyMMddFormatter)

    Image(
        painter = painterResource(id = R.drawable.ic_threelines),
        contentDescription =
        stringResource(R.string.calendar_content_description),
        modifier = Modifier
            .align(Alignment.CenterStart)
            .clickable {
                toggleBottomSheetState(
                    coroutineScope,
                    bottomSheetState,
                )
            }
            .padding(start = 16.dp),
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.align(Alignment.Center),
    ) {
        Image(
            painter = painterResource(id = drawable.ic_back),
            contentDescription = stringResource(id = R.string.back_month_arrow_content_description),
            modifier = Modifier
                .padding(end = 20.dp)
                .clickable {
                    onDateSelected(selectedDate.minusMonths(1))
                    onCalendarMonthChanged()
                },
        )

        Text(
            text = date.substring(
                YEAR_MONTH_START_INDEX,
                YEAR_MONTH_END_INDEX,
            ),
            style = WappTheme.typography.titleBold,
            color = WappTheme.colors.white,
        )

        Image(
            painter = painterResource(id = drawable.ic_forward),
            contentDescription =
            stringResource(id = R.string.forward_month_arrow_content_description),
            modifier = Modifier
                .padding(start = 20.dp)
                .clickable {
                    onDateSelected(selectedDate.plusMonths(1))
                    onCalendarMonthChanged()
                },
        )
    }

    AnimatedVisibility(
        visible = selectedDate != generateNowDate(),
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(end = 16.dp),
    ) {
        Image(
            painter = painterResource(id = drawable.ic_return),
            contentDescription =
            stringResource(R.string.return_today_content_description),
            modifier = Modifier.clickable {
                onDateSelected(generateNowDate())
                onCalendarMonthChanged()
            },
        )
    }
}

@Composable
private fun CalendarBody(
    selectedDate: LocalDate,
    eventsDate: List<LocalDate>,
    onDateSelected: (LocalDate) -> Unit,
) {
    CalendarWeekDays()
    CalendarMonthItem(
        eventDates = eventsDate,
        selectedDate = selectedDate,
        onDateSelected = onDateSelected,
    )
}

@Composable
private fun CalendarWeekDays(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        DaysOfWeek.values().forEach { dayOfWeek ->
            val textColor = when (dayOfWeek) {
                DaysOfWeek.SATURDAY -> WappTheme.colors.blueA3
                DaysOfWeek.SUNDAY -> WappTheme.colors.red
                else -> WappTheme.colors.white
            }

            Text(
                text = dayOfWeek.displayName,
                textAlign = TextAlign.Center,
                color = textColor,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp),
            )
        }
    }
}

@Composable
private fun CalendarMonthItem(
    selectedDate: LocalDate,
    eventDates: List<LocalDate>,
    onDateSelected: (LocalDate) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(DAYS_IN_WEEK),
        modifier = Modifier.fillMaxWidth(),
    ) {
        val visibleDaysFromLastMonth = calculateVisibleDaysFromLastMonth(selectedDate)
        val beforeMonthDaysToShow = generateBeforeMonthDaysToShow(
            visibleDaysFromLastMonth,
            selectedDate,
        )
        itemsIndexed(beforeMonthDaysToShow) { index, day ->
            CalendarDayText(
                text = day.toString(),
                color = getDayColor(index + 1).copy(alpha = ALPHA_DIM),
            )
        }

        val thisMonthLastDate = selectedDate.lengthOfMonth()
        val thisMonthFirstDayOfWeek = selectedDate.withDayOfMonth(1).dayOfWeek
        val thisMonthDaysToShow: List<Int> = (1..thisMonthLastDate).toList()
        items(thisMonthDaysToShow) { day ->
            val currentLocalDate = LocalDate.of(
                selectedDate.year,
                selectedDate.month,
                day,
            )

            val isEvent = (currentLocalDate in eventDates)
            val isSelected = (day == selectedDate.dayOfMonth)
            CalendarDayText(
                text = day.toString(),
                color = getDayColor(day + thisMonthFirstDayOfWeek.value),
                isEvent = isEvent,
                isSelected = isSelected,
                modifier = Modifier.clickable { onDateSelected(currentLocalDate) },
            )
        }

        val remainingDays =
            DAYS_IN_WEEK - (visibleDaysFromLastMonth + thisMonthDaysToShow.size) % DAYS_IN_WEEK
        val nextMonthDaysToShow = IntRange(1, remainingDays).toList()
        items(nextMonthDaysToShow) { day ->
            CalendarDayText(
                text = day.toString(),
                color =
                getDayColor(visibleDaysFromLastMonth + thisMonthDaysToShow.size + day)
                    .copy(alpha = ALPHA_DIM),
            )
        }
    }
}

@Composable
private fun CalendarDayText(
    text: String,
    color: Color,
    isSelected: Boolean = false,
    isEvent: Boolean = false,
    modifier: Modifier = Modifier,
) {
    var columnModifier = modifier
        .padding(
            horizontal = 10.dp,
            vertical = 5.dp,
        )

    if (isSelected) {
        columnModifier = columnModifier.background(
            color = WappTheme.colors.gray82.copy(alpha = 0.4F),
            shape = RoundedCornerShape(5.dp),
        )
    }

    columnModifier = columnModifier.padding(vertical = 5.dp)

    Column(
        modifier = columnModifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = color,
            )
        }
        EventDot(isEvent)
    }
}

@Composable
private fun EventDot(isEvent: Boolean) {
    var boxModifier = Modifier
        .padding(top = 5.dp)
        .size(5.dp)

    if (isEvent) {
        boxModifier = boxModifier
            .aspectRatio(1f)
            .background(
                color = WappTheme.colors.yellow34,
                shape = CircleShape,
            )
    }

    Box(modifier = boxModifier)
}

@Composable
private fun getDayColor(day: Int): Color = when (day % DAYS_IN_WEEK) {
    SUNDAY -> WappTheme.colors.red
    SATURDAY -> WappTheme.colors.blueA3
    else -> WappTheme.colors.white
}

private fun generateBeforeMonthDaysToShow(
    visibleDaysFromLastMonth: Int,
    currentDate: LocalDate,
): List<Int> {
    val beforeMonth = currentDate.minusMonths(1)
    val beforeMonthLastDay = beforeMonth.lengthOfMonth()
    return IntRange(beforeMonthLastDay - visibleDaysFromLastMonth + 1, beforeMonthLastDay).toList()
}

@OptIn(ExperimentalMaterial3Api::class)
private fun toggleBottomSheetState(
    coroutineScope: CoroutineScope,
    sheetState: SheetState,
) = coroutineScope.launch {
    when (sheetState.currentValue) {
        SheetValue.Expanded -> sheetState.partialExpand()
        SheetValue.PartiallyExpanded -> sheetState.expand()
        SheetValue.Hidden -> sheetState.expand()
    }
}

private fun calculateVisibleDaysFromLastMonth(currentDate: LocalDate): Int {
    val firstDayOfWeek: DayOfWeek = currentDate.withDayOfMonth(1).dayOfWeek

    var count = 0
    for (day in DaysOfWeek.values()) {
        if (day.name == firstDayOfWeek.getDisplayName(TextStyle.FULL, Locale.US).uppercase()) {
            break
        }
        count += 1
    }

    return count
}

private const val ALPHA_DIM = 0.3F
private const val SUNDAY = 1
private const val SATURDAY = 0
