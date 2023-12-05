package com.wap.wapp.feature.management.registration.event.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.wap.wapp.feature.management.registration.event.EventRegistrationRoute

const val eventRegistrationNavigationRoute = "event_registration_route"

fun NavController.navigateToEventRegistration(navOptions: NavOptions? = navOptions {}) {
    this.navigate(eventRegistrationNavigationRoute, navOptions)
}

fun NavGraphBuilder.eventRegistrationScreen(
    navigateToManagement: () -> Unit,
) {
    composable(route = eventRegistrationNavigationRoute) {
        EventRegistrationRoute(
            navigateToManagement = navigateToManagement
        )
    }
}
