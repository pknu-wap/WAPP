package com.wap.wapp.feature.profile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
internal fun UserProfile(eventsState: ProfileViewModel.EventsState) {
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        handleMonthEventsState(eventsState = eventsState)

        MyAttendanceStatus(modifier = Modifier.padding(top = 20.dp))

        MySurveyHistory(modifier = Modifier.padding(vertical = 20.dp))
    }
}

@Composable
private fun handleMonthEventsState(
    eventsState: ProfileViewModel.EventsState,
) = when (eventsState) {
    is ProfileViewModel.EventsState.Loading -> CircleLoader(modifier = Modifier.fillMaxSize())
    is ProfileViewModel.EventsState.Success -> {
        ProfileAttendanceCard(
            events = eventsState.events,
            modifier = Modifier.padding(top = 20.dp),
        )
    }
}

@Composable
private fun ProfileAttendanceCard(
    events: List<Event>,
    modifier: Modifier,
) {
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

            if (events.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.no_event_today),
                    style = WappTheme.typography.contentRegular.copy(fontSize = 20.sp),
                    color = WappTheme.colors.white,
                    modifier = Modifier.padding(top = 5.dp),
                )
            } else {
                Text(
                    text = generateTodayEventString(events = events),
                    style = WappTheme.typography.contentRegular.copy(fontSize = 20.sp),
                    color = WappTheme.colors.white,
                    modifier = Modifier.padding(top = 5.dp),
                )
            }
        }
    }
}

@Composable
private fun MyAttendanceStatus(modifier: Modifier = Modifier) {
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
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(vertical = 10.dp),
            ) {
                WappAttendacneRow(isAttendance = false)
                WappAttendacneRow(isAttendance = true)
                WappAttendacneRow(isAttendance = false)
                WappAttendacneRow(isAttendance = true)
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
    append("오늘은")

    withStyle(
        style = SpanStyle(
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline,
        ),
    ) {
        events.forEach { event ->
            append(event.title + ", ")
        }
    }

    append("날 이에요!")
}
