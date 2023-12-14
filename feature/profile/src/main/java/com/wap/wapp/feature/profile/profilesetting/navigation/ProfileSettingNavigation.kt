package com.wap.wapp.feature.profile.profilesetting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.wap.wapp.feature.profile.profilesetting.ProfileSettingRoute

const val profileSettingNavigationRoute = "profile_setting_route"

fun NavController.navigateToProfileSetting(navOptions: NavOptions? = navOptions {}) {
    this.navigate(profileSettingNavigationRoute, navOptions)
}

fun NavGraphBuilder.profileSettingScreen() {
    composable(route = profileSettingNavigationRoute) {
        ProfileSettingRoute()
    }
}
