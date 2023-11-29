package com.wap.wapp.feature.notice

import androidx.compose.foundation.Image
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
import androidx.compose.material.Divider
import androidx.compose.material.Text
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
import com.wap.wapp.core.commmon.util.DateUtil.MONTH_DATE_START_INDEX
import com.wap.wapp.core.commmon.util.DateUtil.yyyyMMddFormatter
import com.wap.wapp.core.model.event.Event
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
internal fun BottomSheetContent(
    expandableHeight: Dp,
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
            modifier = Modifier.padding(start = 15.dp, bottom = 15.dp),
        )
        HandleEventsState(events = events)
    }
}

@Composable
private fun HandleEventsState(events: NoticeViewModel.EventsState) = when (events) {
    is NoticeViewModel.EventsState.Loading -> CircleLoader(modifier = Modifier.fillMaxSize())
    is NoticeViewModel.EventsState.Success -> EventsList(events.events)
    is NoticeViewModel.EventsState.Failure -> Unit
}

@Composable
private fun EventsList(events: List<Event>) {
    if (events.size > 0) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            itemsIndexed(
                items = events,
                key = { _, event -> event.title },
            ) { _, event ->
                EventItem(event = event)
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
                contentDescription = stringResource(id = R.string.bottomSheetCatContextDescription),
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
                text = event.time,
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
                    text = event.title,
                    style = WappTheme.typography.contentRegular,
                    color = WappTheme.colors.white,
                )
                Text(
                    text = event.time,
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
