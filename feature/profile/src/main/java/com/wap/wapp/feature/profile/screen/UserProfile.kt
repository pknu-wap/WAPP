package com.wap.wapp.feature.profile.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wap.designsystem.WappTheme
import com.wap.designsystem.component.CircleLoader
import com.wap.designsystem.component.WappCard
import com.wap.wapp.core.commmon.util.DateUtil
import com.wap.wapp.core.designresource.R.drawable
import com.wap.wapp.core.model.event.Event
import com.wap.wapp.feature.profile.ProfileViewModel
import com.wap.wapp.feature.profile.R
import com.wap.wapp.feature.profile.component.WappAttendacneRow
import com.wap.wapp.feature.profile.component.WappSurveyHistoryRow

@Composable
internal fun UserProfile(
    todayEventsState: ProfileViewModel.EventsState,
    recentEventsState: ProfileViewModel.EventsState,
) {
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        ProfileAttendanceCard(
            todayEventsState = todayEventsState,
            modifier = Modifier.padding(top = 20.dp),
        )
        MyAttendanceStatus(
            recentEventsState = recentEventsState,
            modifier = Modifier.padding(top = 20.dp),
        )

        MySurveyHistory(modifier = Modifier.padding(vertical = 20.dp))
    }
}

@Composable
private fun ProfileAttendanceCard(
    todayEventsState: ProfileViewModel.EventsState,
    modifier: Modifier,
) {
    when (todayEventsState) {
        is ProfileViewModel.EventsState.Loading -> CircleLoader(modifier = Modifier.fillMaxSize())
        is ProfileViewModel.EventsState.Success -> {
            WappCard(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.wap_attendance),
                            style = WappTheme.typography.captionBold.copy(fontSize = 20.sp),
                            color = WappTheme.colors.white,
                        )

                        Image(
                            painter = painterResource(id = drawable.ic_check),
                            contentDescription = "",
                            modifier = Modifier.padding(start = 10.dp),
                        )
                    }
                    Text(
                        text = DateUtil.generateNowDate().format(DateUtil.yyyyMMddFormatter),
                        style = WappTheme.typography.contentRegular,
                        color = WappTheme.colors.white,
                        modifier = Modifier.padding(top = 20.dp),
                    )

                    if (todayEventsState.events.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.no_event_today),
                            style = WappTheme.typography.contentRegular.copy(fontSize = 20.sp),
                            color = WappTheme.colors.white,
                            modifier = Modifier.padding(top = 5.dp),
                        )
                    } else {
                        Text(
                            text = generateTodayEventString(events = todayEventsState.events),
                            style = WappTheme.typography.contentRegular.copy(fontSize = 20.sp),
                            color = WappTheme.colors.white,
                            modifier = Modifier.padding(top = 5.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MyAttendanceStatus(
    recentEventsState: ProfileViewModel.EventsState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.my_attendance),
            style = WappTheme.typography.titleBold.copy(fontSize = 20.sp),
            color = WappTheme.colors.white,
            modifier = Modifier.padding(start = 5.dp),
        )

        WappCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 10.dp),
        ) {
            when (recentEventsState) {
                is ProfileViewModel.EventsState.Loading -> CircleLoader(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .height(130.dp),
                )

                is ProfileViewModel.EventsState.Success -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .padding(10.dp)
                            .height(130.dp),
                    ) {
                        items(
                            items = recentEventsState.events,
                            key = { event -> event.eventId },
                        ) { event ->
                            Log.d("test", recentEventsState.events.toString())
                            WappAttendacneRow(isAttendance = true, event = event)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MySurveyHistory(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.survey_i_did),
            style = WappTheme.typography.titleBold.copy(fontSize = 20.sp),
            color = WappTheme.colors.white,
            modifier = Modifier.padding(start = 5.dp),
        )

        WappCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 10.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(vertical = 10.dp),
            ) {
                WappSurveyHistoryRow()
                WappSurveyHistoryRow()
                WappSurveyHistoryRow()
                WappSurveyHistoryRow()
            }
        }
    }
}

@Composable
private fun generateTodayEventString(events: List<Event>) = buildAnnotatedString {
    append("오늘은 ")

    withStyle(
        style = SpanStyle(
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline,
        ),
    ) {
        append(events.map { it.title }.joinToString(separator = ", "))
    }

    append(" 날 이에요!")
}
