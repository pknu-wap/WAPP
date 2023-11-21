package com.wap.wapp.feature.notice

import android.util.Log
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(
    coroutineScope: CoroutineScope,
    bottomSheetState: SheetState,
    dateAndDayOfWeek: Pair<String, String>,
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
        CalendarBody()
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
fun CalendarBody() {
    DayOfWeek()
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
    val lastDay = currentDate.lengthOfMonth()
    val firstDayOfWeek = currentDate.withDayOfMonth(1).dayOfWeek
    val days: List<Int> = (1..lastDay).toList()

    Log.d("test", selectedDate.toString())

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxWidth(),
    ) {
        val beforeMonthDate = calculateBeforeMonthDate(currentDate)
        val beforeMonth = currentDate.minusMonths(1)
        val beforeMonthLastDay = beforeMonth.lengthOfMonth()

        items(IntRange(beforeMonthLastDay - beforeMonthDate, beforeMonthLastDay).toList()) { idx ->
            CalendarDayText(
                text = idx.toString(),
                color = when ((idx + 1) % 7) {
                    1 -> Color.Red
                    0 -> Color.Blue
                    else -> Color.White
                }.copy(alpha = 0.3F),
            )
        }

        items(days) { day ->
            val date = currentDate.withDayOfMonth(day)
            val formatter = DateTimeFormatter.ofPattern("dd")
            val nowDate = LocalDate.of(
                LocalDate.now().year,
                LocalDate.now().month,
                day,
            )

            CalendarDayText(
                text = formatter.format(date),
                color = when (((day + firstDayOfWeek.value + 1)) % 7) {
                    1 -> Color.Red
                    0 -> Color.Blue
                    else -> Color.White
                },
                isEvent = if (nowDate in eventDates) {
                    true
                } else {
                    false
                },
                isSelect = (day == selectedDate.dayOfMonth),
            )
        }

        val remainingDays = 7 - ((beforeMonthDate + 1 + days.size) % 7)

        items(IntRange(1, remainingDays).toList()) { idx ->
            CalendarDayText(
                text = idx.toString(),
                color = when ((beforeMonthDate + days.size + idx + 1) % 7) {
                    1 -> Color.Red
                    0 -> Color.Blue
                    else -> Color.White
                }.copy(alpha = 0.3F),
            )
        }
    }
}

@Composable
fun CalendarDayText(
    text: String,
    color: Color,
    isSelect: Boolean = false,
    isEvent: Boolean = false,
    onClick: (Unit) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.clickable { onClick },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = if (isSelect) {
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
                color = if (isSelect && color == Color.White) Color.Black else color,
                textAlign = TextAlign.Center,
                modifier = modifier,
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

private fun calculateBeforeMonthDate(currentDate: LocalDate): Int {
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
