package com.wap.wapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.wap.wapp.R
import com.wap.wapp.core.designresource.R.string
import com.wap.wapp.feature.attendance.navigation.attendanceNavigationRoute
import com.wap.wapp.feature.management.navigation.managementNavigationRoute
import com.wap.wapp.feature.notice.navigation.noticeNavigationRoute
import com.wap.wapp.feature.profile.navigation.profileNavigationRoute
import com.wap.wapp.feature.survey.navigation.SurveyRoute

enum class TopLevelDestination(
    val route: String,
    @DrawableRes val iconDrawableId: Int,
    @StringRes val labelTextId: Int,
) {
    NOTICE(
        route = noticeNavigationRoute,
        iconDrawableId = R.drawable.ic_notice,
        labelTextId = string.notice,
    ),
    SURVEY(
        route = SurveyRoute.route,
        iconDrawableId = R.drawable.ic_survey,
        labelTextId = string.survey,
    ),
    ATTENDANCE(
        route = attendanceNavigationRoute,
        iconDrawableId = com.wap.wapp.core.designresource.R.drawable.ic_check,
        labelTextId = com.wap.wapp.feature.attendance.R.string.attendance,
    ),
    PROFILE(
        route = profileNavigationRoute,
        iconDrawableId = R.drawable.ic_profile,
        labelTextId = string.profile,
    ),
    MANAGEMENT(
        route = managementNavigationRoute,
        iconDrawableId = R.drawable.ic_management,
        labelTextId = string.management,
    ),
}
