package com.wap.wapp.feature.attendance.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.wap.wapp.feature.attendance.AttendanceRoute

const val attendanceNavigationRoute = "attendance/{userId}"

fun NavController.navigateToAttendance(
    userId: String,
    navOptions: NavOptions? = navOptions {},
) {
    this.navigate("attendance/$userId", navOptions)
}

fun NavGraphBuilder.attendanceScreen(
    navigateToProfile: () -> Unit,
    navigateToAttendanceManagement: (String) -> Unit,
) {
    composable(
        route = attendanceNavigationRoute,
        arguments = listOf(navArgument("userId") { type = NavType.StringType }),
    ) { navBackStackEntry ->
        val userId = navBackStackEntry.arguments?.getString("userId") ?: ""
        AttendanceRoute(
            userId = userId,
            navigateToProfile = navigateToProfile,
            navigateToAttendanceManagement = navigateToAttendanceManagement,
        )
    }
}
