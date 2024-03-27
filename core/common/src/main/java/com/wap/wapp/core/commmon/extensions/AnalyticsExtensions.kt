package com.wap.wapp.core.commmon.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.wap.wapp.core.analytics.AnalyticsEvent
import com.wap.wapp.core.analytics.AnalyticsEvent.Param
import com.wap.wapp.core.analytics.AnalyticsHelper
import com.wap.wapp.core.analytics.LocalAnalyticsHelper

fun AnalyticsHelper.logScreenView(screenName: String) {
    logEvent(
        AnalyticsEvent(
            type = AnalyticsEvent.SCREEN_VIEW,
            extras = listOf(
                Param(AnalyticsEvent.SCREEN_NAME, screenName),
            ),
        ),
    )
}

@Composable
fun TrackScreenViewEvent(
    screenName: String,
    analyticsHelper: AnalyticsHelper = LocalAnalyticsHelper.current,
) = LaunchedEffect(Unit) {
    analyticsHelper.logScreenView(screenName)
}
