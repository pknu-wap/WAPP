package com.wap.wapp.feature.management.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.wap.wapp.feature.management.ManagementRoute

const val managementNavigationRoute = "management_route"

fun NavController.navigateToManagement(navOptions: NavOptions? = navOptions {}) {
    this.navigate(managementNavigationRoute, navOptions)
}

fun NavGraphBuilder.managementScreen(
    navigateToEventRegistration: () -> Unit,
    navigateToSurveyRegistration: () -> Unit,
) {
    composable(route = managementNavigationRoute) {
        ManagementRoute(
            navigateToEventRegistration = navigateToEventRegistration,
            navigateToSurveyRegistration = navigateToSurveyRegistration,
        )
    }
}
