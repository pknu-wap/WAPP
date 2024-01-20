package com.wap.wapp.feature.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.wap.wapp.feature.profile.ProfileRoute

const val profileNavigationRoute = "profile_route"

fun NavController.navigateToProfile(navOptions: NavOptions? = navOptions {}) {
    this.navigate(profileNavigationRoute, navOptions)
}

fun NavGraphBuilder.profileScreen(
    navigateToProfileSetting: (String) -> Unit,
    navigateToAttendance: () -> Unit,
    navigateToSignIn: () -> Unit,
) {
    composable(route = profileNavigationRoute) {
        ProfileRoute(
            navigateToProfileSetting = navigateToProfileSetting,
            navigateToAttendance = navigateToAttendance,
            navigateToSignIn = navigateToSignIn,
        )
    }
}
