package com.wap.wapp.feature.management.registration.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wap.designsystem.WappTheme
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.feature.management.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
internal fun WappDatePickerDialog(
    selectedDate: LocalDate = DateUtil.generateNowDate(),
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmButtonClicked: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(shape = RoundedCornerShape(10.dp)) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .background(color = WappTheme.colors.black25)
                    .padding(10.dp),
            ) {
                CalendarHeader(
                    selectedDate = selectedDate,
                    onDateSelected = onDateSelected,
                )
                CalendarBody(
                    selectedDate = selectedDate,
                    onDateSelected = onDateSelected,
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        color = WappTheme.colors.grayBD,
                        style = WappTheme.typography.contentBold,
                        modifier = Modifier
                            .padding(end = 30.dp)
                            .clickable { onDismissRequest() },
                    )

                    Text(
                        stringResource(R.string.select),
                        color = WappTheme.colors.grayBD,
                        style = WappTheme.typography.contentBold,
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .clickable { onConfirmButtonClicked() },
                    )
                }
            }
        }
    }
}

@Composable
private fun CalendarHeader(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
) = Box(modifier = Modifier.fillMaxWidth()) {
    val date = selectedDate.format(DateUtil.yyyyMMddFormatter)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.align(Alignment.Center),
    ) {
        Image(
            painter = painterResource(id = com.wap.wapp.core.designresource.R.drawable.ic_back),
            contentDescription = stringResource(id = R.string.backMonthArrowContentDescription),
            modifier = Modifier
                .padding(end = 20.dp)
                .clickable { onDateSelected(selectedDate.minusMonths(1)) },
        )

        Text(
            text = date.substring(
                DateUtil.YEAR_MONTH_START_INDEX,
                DateUtil.YEAR_MONTH_END_INDEX,
            ),
            style = WappTheme.typography.titleBold,
            color = WappTheme.colors.white,
        )

        Image(
            painter = painterResource(id = com.wap.wapp.core.designresource.R.drawable.ic_forward),
            contentDescription = stringResource(id = R.string.forwardMonthArrowContentDescription),
            modifier = Modifier
                .padding(start = 20.dp)
                .clickable { onDateSelected(selectedDate.plusMonths(1)) },
        )
    }
}

@Composable
private fun CalendarBody(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
) {
    DayOfWeek()
    CalendarMonthItem(
        selectedDate = selectedDate,
        onDateSelected = onDateSelected,
    )
}

@Composable
private fun DayOfWeek(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        DateUtil.DaysOfWeek.values().forEach { dayOfWeek ->
            val textColor = when (dayOfWeek) {
                DateUtil.DaysOfWeek.SATURDAY -> WappTheme.colors.blueA3
                DateUtil.DaysOfWeek.SUNDAY -> WappTheme.colors.red
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
    onDateSelected: (LocalDate) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(DateUtil.DAYS_IN_WEEK),
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
            val date = selectedDate.withDayOfMonth(day)
            val currentLocalDate = LocalDate.of(
                selectedDate.year,
                selectedDate.month,
                day,
            )

            val isSelected = (day == selectedDate.dayOfMonth)
            CalendarDayText(
                text = DateUtil.ddFormatter.format(date),
                color = getDayColor(day + thisMonthFirstDayOfWeek.value),
                isSelected = isSelected,
                modifier = Modifier.clickable { onDateSelected(currentLocalDate) },
            )
        }

        val remainingDays =
            DateUtil.DAYS_IN_WEEK - (visibleDaysFromLastMonth + thisMonthDaysToShow.size) %
                DateUtil.DAYS_IN_WEEK
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
    modifier: Modifier = Modifier,
) {
    var columnModifier = modifier.padding(5.dp)

    if (isSelected) {
        columnModifier = columnModifier.background(
            color = WappTheme.colors.gray82.copy(alpha = 0.4F),
            shape = RoundedCornerShape(5.dp),
        )
    }

    columnModifier = columnModifier.padding(5.dp)

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
    }
}

@Composable
private fun getDayColor(day: Int): Color = when (day % DateUtil.DAYS_IN_WEEK) {
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
