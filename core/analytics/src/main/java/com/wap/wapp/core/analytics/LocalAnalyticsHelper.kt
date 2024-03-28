package com.wap.wapp.core.analytics

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAnalyticsHelper = staticCompositionLocalOf<AnalyticsHelper> {
    error("Any AnalyticsHelper Did Not Provided")
}
