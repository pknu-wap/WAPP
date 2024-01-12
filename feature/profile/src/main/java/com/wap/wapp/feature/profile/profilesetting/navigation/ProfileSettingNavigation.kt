package com.wap.wapp.feature.profile.profilesetting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.wap.wapp.feature.profile.profilesetting.ProfileSettingRoute

const val profileSettingNavigationRoute = "profile_setting_route/{userId}"

fun NavController.navigateToProfileSetting(
    userId: String,
    navOptions: NavOptions? = navOptions {},
) {
    this.navigate("profile_setting_route/$userId", navOptions)
}

fun NavGraphBuilder.profileSettingScreen(navigateToProfile: () -> Unit) {
    composable(
        route = profileSettingNavigationRoute,
        arguments = listOf(
            navArgument("userId") { type = NavType.StringType },
        ),
    ) { navBackStackEntry ->
        val userId = navBackStackEntry.arguments?.getString("userId") ?: ""
        ProfileSettingRoute(userId = userId, navigateToProfile = navigateToProfile)
    }
}
