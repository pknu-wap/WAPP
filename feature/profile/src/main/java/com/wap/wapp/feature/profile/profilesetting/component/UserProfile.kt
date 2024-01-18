package com.wap.wapp.feature.profile.profilesetting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    userRespondedSurveysState: ProfileViewModel.SurveysState,
    attendanceCardBoardColor: Color,
) {
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        ProfileAttendanceCard(
            todayEventsState = todayEventsState,
            attendanceCardBoardColor = attendanceCardBoardColor,
            modifier = Modifier.padding(top = 20.dp),
        )

        MyAttendanceStatus(
            recentEventsState = recentEventsState,
            modifier = Modifier.padding(top = 20.dp),
        )

        MySurveyHistory(
            userRespondedSurveysState = userRespondedSurveysState,
            modifier = Modifier.padding(vertical = 20.dp),
        )
    }
}

@Composable
private fun ProfileAttendanceCard(
    todayEventsState: ProfileViewModel.EventsState,
    attendanceCardBoardColor: Color,
    modifier: Modifier,
) {
    when (todayEventsState) {
        is ProfileViewModel.EventsState.Loading -> CircleLoader(modifier = Modifier.fillMaxSize())
        is ProfileViewModel.EventsState.Success -> {
            val cardModifier = if (todayEventsState.events.isNotEmpty()) {
                modifier
                    .border(
                        width = 2.dp,
                        color = attendanceCardBoardColor,
                        shape = RoundedCornerShape(10.dp),
                    )
                    .fillMaxWidth()
                    .height(130.dp)
                    .clickable { }
            } else {
                modifier
                    .fillMaxWidth()
                    .height(130.dp)
            }

            WappCard(
                modifier = cardModifier,
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)) {
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
                    if (todayEventsState.events.isNotEmpty()) {
                        Image(
                            painter = painterResource(id = drawable.ic_forward),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 12.dp),
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
                .height(130.dp)
                .padding(top = 10.dp),
        ) {
            when (recentEventsState) {
                is ProfileViewModel.EventsState.Loading -> CircleLoader(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .height(130.dp),
                )

                is ProfileViewModel.EventsState.Success -> {
                    if (recentEventsState.events.isEmpty()) {
                        NothingToShow(title = R.string.no_events_recently)
                        return@WappCard
                    }

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
                            WappAttendacneRow(isAttendance = true, event = event)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MySurveyHistory(
    userRespondedSurveysState: ProfileViewModel.SurveysState,
    modifier: Modifier = Modifier,
) {
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
                .height(130.dp)
                .padding(top = 10.dp),
        ) {
            when (userRespondedSurveysState) {
                is ProfileViewModel.SurveysState.Loading -> CircleLoader(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .height(130.dp),
                )

                is ProfileViewModel.SurveysState.Success -> {
                    if (userRespondedSurveysState.surveys.isEmpty()) {
                        NothingToShow(title = R.string.no_surveys_after_sign_up)
                        return@WappCard
                    }

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .padding(10.dp)
                            .height(130.dp),
                    ) {
                        items(
                            items = userRespondedSurveysState.surveys,
                            key = { survey -> survey.surveyId },
                        ) { survey ->
                            WappSurveyHistoryRow(survey)
                        }
                    }
                }
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
