package com.wap.wapp.feature.attendance.management.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.wap.wapp.feature.attendance.management.AttendanceManagementRoute

const val attendanceManagementNavigationRoute = "attendance/management"

fun NavController.navigateToAttendanceManagement(
    navOptions: NavOptions? = navOptions {},
) = this.navigate(attendanceManagementNavigationRoute, navOptions)

fun NavGraphBuilder.attendanceManagementScreen(navigateToManagement: (String) -> Unit) {
    composable(route = attendanceManagementNavigationRoute) {
        AttendanceManagementRoute(navigateToManagement = navigateToManagement)
    }
}
