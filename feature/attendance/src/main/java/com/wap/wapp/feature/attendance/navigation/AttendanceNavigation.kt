package com.wap.wapp.feature.attendance.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.wap.wapp.feature.attendance.AttendanceRoute

const val attendanceNavigationRoute = "attendance"

fun NavController.navigateToAttendance(navOptions: NavOptions? = navOptions {}) =
    this.navigate(attendanceNavigationRoute, navOptions)

fun NavGraphBuilder.attendanceScreen(
    navigateToSignIn: () -> Unit,
    navigateToAttendanceManagement: () -> Unit,
) {
    composable(route = attendanceNavigationRoute) {
        AttendanceRoute(
            navigateToSignIn = navigateToSignIn,
            navigateToAttendanceManagement = navigateToAttendanceManagement,
        )
    }
}
