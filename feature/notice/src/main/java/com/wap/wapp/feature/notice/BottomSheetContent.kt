package com.wap.wapp.feature.notice

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.CircleLoader
import com.wap.wapp.core.commmon.util.DateUtil.HHmmFormatter
import com.wap.wapp.core.commmon.util.DateUtil.MONTH_DATE_START_INDEX
import com.wap.wapp.core.commmon.util.DateUtil.yyyyMMddFormatter
import com.wap.wapp.core.model.event.Event
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BottomSheetContent(
    expandableHeight: Dp,
    bottomSheetState: SheetState,
    events: NoticeViewModel.EventsState,
    selectedDate: LocalDate,
) {
    val date = yyyyMMddFormatter.format(selectedDate).substring(MONTH_DATE_START_INDEX)
    val dayOfWeek = selectedDate.dayOfWeek.getDisplayName(
        TextStyle.FULL,
        Locale.KOREAN,
    )

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.height(expandableHeight),
    ) {
        Text(
            text = "$date $dayOfWeek",
            style = WappTheme.typography.titleBold,
            color = WappTheme.colors.white,
            modifier = Modifier.padding(start = 15.dp, bottom = 5.dp),
        )

        when (events) {
            is NoticeViewModel.EventsState.Loading ->
                CircleLoader(modifier = Modifier.fillMaxWidth())

            is NoticeViewModel.EventsState.Success -> EventsList(
                bottomSheetState = bottomSheetState,
                events = events.events,
            )

            is NoticeViewModel.EventsState.Failure -> Unit
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventsList(
    bottomSheetState: SheetState,
    events: List<Event>,
) {
    if (events.isNotEmpty()) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(
                items = events,
                key = { event -> event.eventId },
            ) { event ->
                EventItem(
                    bottomSheetState = bottomSheetState,
                    event = event,
                )
            }
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_cat),
                contentDescription = stringResource(id = R.string.bottomSheetCatContentDescription),
            )

            Text(
                text = stringResource(id = R.string.bottomSheetNoEvent),
                style = WappTheme.typography.titleMedium,
                color = WappTheme.colors.white,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 30.dp),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventItem(
    bottomSheetState: SheetState,
    event: Event,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(vertical = 10.dp)
                .height(IntrinsicSize.Min)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = event.startDateTime.toLocalTime().format(HHmmFormatter),
                style = WappTheme.typography.contentBold,
                color = WappTheme.colors.white,
            )

            Spacer(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(10.dp))
                    .background(WappTheme.colors.yellow34),
            )

            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = event.title,
                    style = WappTheme.typography.contentRegular,
                    color = WappTheme.colors.white,
                )

                if (bottomSheetState.currentValue == SheetValue.Expanded) {
                    Text(
                        text = event.location,
                        style = WappTheme.typography.captionRegular,
                        color = WappTheme.colors.yellow34,
                    )

                    Text(
                        text = event.content,
                        style = WappTheme.typography.captionRegular,
                        color = WappTheme.colors.grayBD,
                    )
                }

                Text(
                    text = event.startDateTime.format(HHmmFormatter) + " ~ " +
                        event.endDateTime.format(HHmmFormatter),
                    style = WappTheme.typography.captionRegular,
                    color = WappTheme.colors.yellow34,
                )
            }
        }

        Divider(
            color = WappTheme.colors.gray82,
            thickness = 1.dp,
        )
    }
}
