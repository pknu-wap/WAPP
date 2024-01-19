package com.wap.wapp.feature.attendance.management.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.wap.wapp.feature.attendance.management.AttendanceManagementRoute

const val attendanceManagementNavigationRoute = "attendance/management/{userId}"

fun NavController.navigateToAttendanceManagemenet(
    userId: String,
    navOptions: NavOptions? = navOptions {},
) {
    this.navigate("attendance/management/$userId", navOptions)
}

fun NavGraphBuilder.attendanceManagementScreen(navigateToAttendance: (String) -> Unit) {
    composable(
        route = attendanceManagementNavigationRoute,
        arguments = listOf(navArgument("userId") { type = NavType.StringType }),
    ) { navBackStackEntry ->
        val userId = navBackStackEntry.arguments?.getString("userId") ?: ""
        AttendanceManagementRoute(
            userId = userId,
            navigateToAttendance = navigateToAttendance,
        )
    }
}
