package com.wap.wapp.feature.notice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import com.wap.wapp.feature.notice.DateUtil.Companion.DAYS_IN_WEEK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(
    coroutineScope: CoroutineScope,
    bottomSheetState: SheetState,
    dateAndDayOfWeek: Pair<String, String>,
    currentDate: LocalDate,
    eventDates: List<LocalDate>,
    selectedDate: LocalDate,
    measureDefaultModifier: Modifier,
    measureExpandableModifier: Modifier,
) {
    Column(
        modifier = measureDefaultModifier,
    ) {
        CalendarHeader(
            coroutineScope = coroutineScope,
            bottomSheetState = bottomSheetState,
            dateAndDayOfWeek = dateAndDayOfWeek,
            modifier = measureExpandableModifier,
        )
        CalendarBody(
            currentDate = currentDate,
            eventsDate = eventDates,
            selectedDate = selectedDate,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarHeader(
    coroutineScope: CoroutineScope,
    bottomSheetState: SheetState,
    dateAndDayOfWeek: Pair<String, String>,
    modifier: Modifier,
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.ic_threelines),
        contentDescription =
        stringResource(R.string.calendarToggleImageContextDescription),
        modifier = Modifier
            .clickable {
                toggleBottomSheetState(
                    coroutineScope,
                    bottomSheetState,
                )
            }
            .padding(start = 16.dp),
    )
    Text(
        text = dateAndDayOfWeek.first.substring(
            DateUtil.YEAR_MONTH_START_INDEX,
            DateUtil.YEAR_MONTH_END_INDEX,
        ),
        style = WappTheme.typography.titleBold,
        color = WappTheme.colors.white,
        modifier = Modifier.padding(start = 10.dp),
    )
}

@Composable
fun CalendarBody(
    currentDate: LocalDate,
    eventsDate: List<LocalDate>,
    selectedDate: LocalDate,
) {
    DayOfWeek()
    CalendarMonthItem(
        currentDate = currentDate,
        eventDates = eventsDate,
        selectedDate = selectedDate,
    )
}

@Composable
fun DayOfWeek(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        DateUtil.DaysOfWeek.values().forEach { dayOfWeek ->
            Text(
                text = dayOfWeek.displayName,
                textAlign = TextAlign.Center,
                color = when (dayOfWeek) {
                    DateUtil.DaysOfWeek.SATURDAY -> Color.Blue
                    DateUtil.DaysOfWeek.SUNDAY -> Color.Red
                    else -> Color.White
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp),
            )
        }
    }
}

@Composable
fun CalendarMonthItem(
    currentDate: LocalDate,
    eventDates: List<LocalDate>,
    selectedDate: LocalDate,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(DAYS_IN_WEEK),
        modifier = Modifier.fillMaxWidth(),
    ) {
        // 이전 달
        val visibleDaysFromLastMonth = calculateVisibleDaysFromLastMonth(currentDate)
        val beforeMonthDaysToShow = generateBeforeMonthDaysToShow(
            visibleDaysFromLastMonth,
            currentDate,
        )
        items(beforeMonthDaysToShow) { day ->
            CalendarDayText(
                text = day.toString(),
                color = getDayColor(day + 1).copy(alpha = ALPHA_DIM),
            )
        }

        // 이번 달
        val thisMonthLastDate = currentDate.lengthOfMonth()
        val thisMonthFirstDayOfWeek = currentDate.withDayOfMonth(1).dayOfWeek
        val thisMonthDaysToShow: List<Int> = (1..thisMonthLastDate).toList()
        items(thisMonthDaysToShow) { day ->
            val date = currentDate.withDayOfMonth(day)
            val currentLocalDate = LocalDate.of(
                LocalDate.now().year,
                LocalDate.now().month,
                day,
            )
            val isEvent = currentLocalDate in eventDates
            val isSelected = (day == selectedDate.dayOfMonth)
            CalendarDayText(
                text = DateUtil.ddFormatter.format(date),
                color = getDayColor(day + thisMonthFirstDayOfWeek.value + 1),
                isEvent = isEvent,
                isSelected = isSelected,
            )
        }

        // 다음 달
        val remainingDays =
            DAYS_IN_WEEK - (visibleDaysFromLastMonth + thisMonthDaysToShow.size + 1) % DAYS_IN_WEEK
        val nextMonthDaysToShow = IntRange(1, remainingDays).toList()
        items(nextMonthDaysToShow) { day ->
            CalendarDayText(
                text = day.toString(),
                color =
                getDayColor(visibleDaysFromLastMonth + thisMonthDaysToShow.size + day + 1)
                    .copy(alpha = ALPHA_DIM),
            )
        }
    }
}

@Composable
fun getDayColor(day: Int): Color = when (day % DAYS_IN_WEEK) {
    SUNDAY -> WappTheme.colors.red
    SATURDAY -> WappTheme.colors.blue
    else -> Color.White
}

private fun generateBeforeMonthDaysToShow(
    visibleDaysFromLastMonth: Int,
    currentDate: LocalDate,
): List<Int> {
    val beforeMonth = currentDate.minusMonths(1)
    val beforeMonthLastDay = beforeMonth.lengthOfMonth()
    return IntRange(beforeMonthLastDay - visibleDaysFromLastMonth, beforeMonthLastDay).toList()
}

@Composable
fun CalendarDayText(
    text: String,
    color: Color,
    isSelected: Boolean = false,
    isEvent: Boolean = false,
    onClick: (Unit) -> Unit = {},
) {
    Column(
        modifier = Modifier.clickable { onClick },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = if (isSelected) {
                Modifier
                    .padding(horizontal = 15.dp, vertical = 8.dp)
                    .aspectRatio(1f)
                    .background(Color.White, shape = CircleShape)
            } else {
                Modifier
                    .padding(horizontal = 15.dp, vertical = 8.dp)
                    .aspectRatio(1f)
            },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                color = if (isSelected && color == Color.White) Color.Black else color,
                textAlign = TextAlign.Center,
            )
        }

        if (isEvent) {
            Box(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .size(5.dp)
                    .aspectRatio(1f)
                    .background(Color.Red, shape = CircleShape),
            )
        } else {
            Box(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .size(5.dp),
            )
        }
    }
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
    for (day in DateUtil.DaysOfWeek.values()) {
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
